package me.jiinwoo.trello.global.config.security

import me.jiinwoo.trello.domain.Member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

class MemberPrincipal private constructor(
    private var email: String,
    private var username: String,
    private var authorities: MutableCollection<out GrantedAuthority>,
    private var password: String,
) : UserDetails {

    companion object {
        fun create(member: Member): MemberPrincipal {
            return MemberPrincipal(
                email = member.email,
                username = member.username,
                authorities = Collections.emptyList(),
                password = member.encryptedPassword
            )
        }
    }

    fun getEmail (): String {
        return email;
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.authorities
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true;
    }

    override fun isAccountNonLocked(): Boolean {
        return true;
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true;
    }

    override fun isEnabled(): Boolean {
        return true;
    }
}