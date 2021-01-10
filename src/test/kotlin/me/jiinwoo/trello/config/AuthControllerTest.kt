package me.jiinwoo.trello.config

import SpringWebTestSupport
import buildMember
import me.jiinwoo.trello.infra.config.security.LoginDTO
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
class AuthControllerTest: SpringWebTestSupport() {

    @Test
    fun `로그인 후 token으로 api 요청이 성공한다` () {
        val savedMember = save(buildMember())
        val dto = LoginDTO.Req(
            email = "mock@email.com",
            password = "mockPassword"
        )
        val andExpect: MvcResult = mockMvc.post("/auth") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(dto)
        }.andDo { print() }.andExpect {
            status { isOk() }
        }.andReturn()
        val token = andExpect.response.contentAsString
        val readValue = objectMapper.readValue(token, MutableMap::class.java)
        val jwtToken = readValue.get("token")
        println("jwtToken = ${jwtToken}")
        mockMvc.get("/api/members") {
            contentType = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer ${jwtToken}")
        }.andDo { print() }.andExpect {
            status {isOk()}
        }
    }
    @Test
    fun `토큰이 없거나 잘못된 토큰을 보낼시 401로 실패한다` (){
        mockMvc.get("/api/members") {
            contentType = MediaType.APPLICATION_JSON
        }.andDo { print() }.andExpect {
            status {isUnauthorized()}
        }

        mockMvc.get("/api/members") {
            contentType = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer ${1231231}")
        }.andDo { print() }.andExpect {
            status {isUnauthorized()}
        }
    }
}