package me.jiinwoo.trello.domain.Member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MemberRepository : JpaRepository<Member, Long>{

    fun findByEmail(email: String): Optional<Member>

}