package me.jiinwoo.trello.infra.config.security

class LoginDTO {

    data class Req(
        val email: String,
        val password: String,
    )

}