import me.jiinwoo.trello.domain.Member.Member
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun buildMember (
    email: String = "mock@email.com",
    username: String = "mockUser",
    password: String = "mockPassword"
    ) = Member(
    email = email,
    username = username,
    encryptedPassword = BCryptPasswordEncoder().encode(password)
)


