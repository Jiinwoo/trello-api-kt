package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.Member.dto.MemberCreateDTO
import me.jiinwoo.trello.infra.config.security.MemberPrincipal
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val memberSignupService: MemberSignupService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createMember(
        @Validated @RequestBody dto: MemberCreateDTO.Req
    ): MemberCreateDTO.Res {
        return memberSignupService.signup(dto);
    }
    @GetMapping
    fun getMember (
        @MemberAuth principal: MemberPrincipal
    ): String {
        println("principal = ${principal.username}")
        println("principal = ${principal.password}")
        println("principal = ${principal.getEmail()}")
        return "asd"
    }
}