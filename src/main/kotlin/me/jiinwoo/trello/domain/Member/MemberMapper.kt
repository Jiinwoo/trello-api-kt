package me.jiinwoo.trello.domain.Member

import me.jiinwoo.trello.domain.Member.dto.MemberCreateDTO
import org.mapstruct.*

@Mapper(
    componentModel = "spring", uses = [PasswordEncoderMapper::class],
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT
)
interface MemberMapper {

    @Mappings(
        Mapping(target = "provider", ignore = true),
        Mapping(source = "password", target = "encryptedPassword", qualifiedBy = [EncodedMapping::class])
    )
    fun toMember(req: MemberCreateDTO.Req): Member
    fun fromMember(member: Member): MemberCreateDTO.Res
}
