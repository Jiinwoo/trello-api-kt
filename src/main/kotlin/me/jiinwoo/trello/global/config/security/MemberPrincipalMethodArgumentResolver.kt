package me.jiinwoo.trello.global.config.security

import me.jiinwoo.trello.domain.Member.MemberAuth
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class MemberPrincipalMethodArgumentResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val memberAuth = parameter.getParameterAnnotation(MemberAuth::class.java)
        if(memberAuth == null) return false
        println("123")
        println(parameter.parameterType)
        return true
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        println("----------------------------------")
//        println("parameter = ${parameter.getParameterAnnotation()}")
        if(parameter.getParameterAnnotation(me.jiinwoo.trello.domain.Member.MemberAuth::class.javaObjectType) == null) {
            return false;
        }
        val auth = SecurityContextHolder.getContext().authentication
        if(auth != null) {
            println("auth = ${auth.principal}")
            return auth.principal
        }
        return null
    }
}