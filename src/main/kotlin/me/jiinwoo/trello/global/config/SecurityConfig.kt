package me.jiinwoo.trello.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import me.jiinwoo.trello.global.config.security.*
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val customerUserDetailsService: CustomUserDetailsService,
    private val jwtUtil: JwtUtil,
    private val customerAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }

    override fun configure(http: HttpSecurity?) {
        http!!
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers(HttpMethod.POST,"/api/members").permitAll()
                .antMatchers(HttpMethod.OPTIONS,"/api/members").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilter(getJwtAuthenticationFilter())
            .addFilter(getJwtAuthorizationFilter())
            .exceptionHandling()
            .authenticationEntryPoint(customerAuthenticationEntryPoint)
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
    fun authenticationProvider (): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(customerUserDetailsService)
        provider.setPasswordEncoder(getPasswordEncoder())
        return provider
    }

    @Bean
    fun getPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}