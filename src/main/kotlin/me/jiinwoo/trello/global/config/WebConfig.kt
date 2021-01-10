package me.jiinwoo.trello.global.config

import me.jiinwoo.trello.global.config.security.MemberPrincipalMethodArgumentResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer{

    @Bean
    fun memberPrincipalMethodArgumentResolver (): MemberPrincipalMethodArgumentResolver {
        return MemberPrincipalMethodArgumentResolver()
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberPrincipalMethodArgumentResolver())
    }
}