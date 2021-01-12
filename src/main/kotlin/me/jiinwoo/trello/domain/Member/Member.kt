package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.model.AuditingEntity
import me.jiinwoo.trello.domain.model.AuthProvider
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
}