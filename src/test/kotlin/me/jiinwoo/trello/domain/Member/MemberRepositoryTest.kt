package me.jiinwoo.trello.domain.Member

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.MariaDBContainer

@SpringBootTest(properties = ["spring.profiles.active=test"])
@Transactional
internal class MemberRepositoryTest {
    @Autowired
    lateinit var memberRepository: MemberRepository


    companion object {
        @JvmStatic
        private val mariadb = MariaDBContainer<Nothing>("mariadb:latest").apply {
            withDatabaseName("test-database")
//            withConfigurationOverride()
        }

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mariadb.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll() {
            mariadb.stop()
        }
    }

    @Test
    fun createMember () {
        val member = Member(
            email = "jwjung5038@gmail.com",
            username = "asd",
            encryptedPassword = "1234"
        )
        val savedMember = memberRepository.save(member)

    }

}