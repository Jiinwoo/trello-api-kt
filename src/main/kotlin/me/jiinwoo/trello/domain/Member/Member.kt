package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.model.AuditingEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "TB_MEMBER")
@Entity
class Member(
    @Column
    var email: String,

    @Column
    var username: String,

    @Column
    var encryptedPassword: String,
) : AuditingEntity() {

    fun updateEmail() {
        this.email = "asd"
    }
}