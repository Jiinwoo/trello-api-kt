package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.model.AuditingEntity
import me.jiinwoo.trello.domain.model.AuthProvider
import me.jiinwoo.trello.global.config.security.oauth2.user.OAuth2UserInfo
import javax.persistence.*

@Table(name = "TB_MEMBER")
@Entity
class Member(
    @Column
    var email: String,

    @Column
    var username: String,

    @Column
    var encryptedPassword: String? = null,

    provider: AuthProvider? = null,

    @Column
    var providerId: String? = null,

    @Column
    var imageUrl: String? = null,
) : AuditingEntity() {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var provider: AuthProvider = provider?: AuthProvider.local

    fun updateEmail() {
        this.email = "asd"
    }

    fun update(oAuth2UserInfo: OAuth2UserInfo): Member {
        this.username = oAuth2UserInfo.name
        this.imageUrl = oAuth2UserInfo.imageUrl
        return this
    }
}