package me.jiinwoo.trello.domain.Member

import SpringWebTestSupport
import me.jiinwoo.trello.config.WithMockCustomUser
import me.jiinwoo.trello.domain.Member.dto.MemberCreateDTO
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional


@Transactional
internal class MemberControllerTest : SpringWebTestSupport() {

    @Test
    fun createMember() {
        val dto = MemberCreateDTO.Req(
            email = "testUser@test.com",
            username = "testUser",
            password = "123"
        )

        mockMvc.post("/api/members") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    @WithMockCustomUser
    fun getMember () {
        mockMvc.get("/api/members") {
        }.andExpect {
            status { isOk() }
        }
    }

}