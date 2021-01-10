package me.jiinwoo.trello.config

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory::class)
annotation class WithMockCustomUser(
    val email: String = "mock@email.com",
    val username: String = "mockUser",
    val password: String = "mockPassword"
)
