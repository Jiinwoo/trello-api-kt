package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.Member.dto.MemberCreateDTO
import me.jiinwoo.trello.domain.Member.exception.EmailDuplicateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MemberSignupService (
    private val memberRepository: MemberRepository,
    private val memberMapper: MemberMapper,
        ) {
    fun signup(dto: MemberCreateDTO.Req): MemberCreateDTO.Res {
        if(memberRepository.existsByEmail(dto.email)) {
            throw EmailDuplicateException(dto.email)
        }
        val member: Member = memberRepository.save(memberMapper.toMember(dto))
        return memberMapper.fromMember(member)
    }
}
