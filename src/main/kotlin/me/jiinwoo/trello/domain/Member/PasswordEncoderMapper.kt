package me.jiinwoo.trello.domain.Member

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderMapper(
    private val passwordEncoder: PasswordEncoder
) {

    @EncodedMapping
    fun encode(value: String): String {
        return passwordEncoder.encode(value)
    }

}