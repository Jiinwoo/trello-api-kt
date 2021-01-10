package me.jiinwoo.trello.config

import me.jiinwoo.trello.domain.Member.Member
import me.jiinwoo.trello.global.config.security.MemberPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockCustomUserSecurityContextFactory: WithSecurityContextFactory<WithMockCustomUser> {

    override fun createSecurityContext(withUser: WithMockCustomUser): SecurityContext {
        val context: SecurityContext = SecurityContextHolder.createEmptyContext()
        println("withUser = ${withUser}")
        val principal = MemberPrincipal.create(
            Member(
                email = withUser.email,
                username = withUser.username,
                encryptedPassword = withUser.password
            )
        )
        val auth: Authentication =
            UsernamePasswordAuthenticationToken(principal, "password", principal.authorities)

        context.authentication = auth;
        return context

    }
}