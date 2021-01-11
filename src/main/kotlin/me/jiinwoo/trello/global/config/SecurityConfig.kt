package me.jiinwoo.trello.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import me.jiinwoo.trello.global.config.security.*
import me.jiinwoo.trello.global.config.security.oauth2.CustomOAuth2UserService
import me.jiinwoo.trello.global.config.security.oauth2.OAuth2AuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.csrf.CsrfFilter
import org.springframework.web.filter.CharacterEncodingFilter

@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val customerUserDetailsService: CustomUserDetailsService,
    private val jwtUtil: JwtUtil,
    private val customerAuthenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val customOAuth2UserService : CustomOAuth2UserService,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(getCustomAuthenticationProvider())
    }

    override fun configure(http: HttpSecurity?) {

        val filter = CharacterEncodingFilter()
        filter.encoding = "UTF-8"
        filter.setForceEncoding(true)
        http!!
            .addFilterBefore(filter, CsrfFilter::class.java)
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.POST,"/api/members").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/api/members").permitAll()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorization")
            .and()
//            .redirectionEndpoint()
//            .baseUri("/oauth2/callback/*")
            .userInfoEndpoint()
            .userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
        http
            .addFilter(getJwtAuthenticationFilter())
            .addFilter(getJwtAuthorizationFilter())
            .exceptionHandling()
            .authenticationEntryPoint(customerAuthenticationEntryPoint)

    }

    @Bean
    fun getCustomAuthenticationProvider (): CustomAuthenticationProvider {
        return CustomAuthenticationProvider(getPasswordEncoder(), customerUserDetailsService)
    }

    @Bean
    fun getJwtAuthenticationFilter(): JwtAuthenticationFilter {
        val filter = JwtAuthenticationFilter(objectMapper)
        filter.setFilterProcessesUrl("/auth")
        filter.setAuthenticationManager( authenticationManager())
        filter.setAuthenticationFailureHandler(getSecurityHandler())
        filter.setAuthenticationSuccessHandler(getSecurityHandler())
        filter.afterPropertiesSet()
        return filter
    }
    @Bean
    fun getJwtAuthorizationFilter(): JwtAuthorizationFilter {
        return JwtAuthorizationFilter(authenticationManager(), customerUserDetailsService, jwtUtil)
    }

    @Bean
    fun getSecurityHandler (): SecurityHandler {
        return SecurityHandler(objectMapper, jwtUtil)
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}