package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.Member.dto.MemberCreateDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [PasswordEncoderMapper::class])
interface MemberMapper {
    @Mapping(source = "password", target = "encryptedPassword", qualifiedBy = [EncodedMapping::class])
    fun toMember(req: MemberCreateDTO.Req): Member
    fun fromMember(member: Member): MemberCreateDTO.Res
}