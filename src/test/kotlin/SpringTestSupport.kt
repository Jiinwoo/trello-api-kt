import com.fasterxml.jackson.databind.ObjectMapper
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.ResourceLoader
import org.springframework.mock.web.MockFilterConfig
import org.springframework.security.config.BeanIds
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.DelegatingFilterProxy
import javax.persistence.EntityManager

@SpringBootTest(properties = ["spring.profiles.active=test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class SpringTestSupport {

    @Autowired
    private lateinit var entityManager: EntityManager

    protected val query: JPAQueryFactory by lazy { JPAQueryFactory(entityManager) }

    protected fun <T> save(entity: T): T {
        entityManager.persist(entity)
        flushAndClearPersistentContext()
        return entity
    }

    protected fun <T> saveAll(entities: Iterable<T>): Iterable<T> {
        for (entity in entities) {
            entityManager.persist(entity)
        }
        flushAndClearPersistentContext()
        return entities
    }

    private fun flushAndClearPersistentContext() {
        entityManager.flush()
        entityManager.clear()
    }
}

@AutoConfigureMockMvc
class SpringWebTestSupport : SpringTestSupport() {

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    @Autowired
    protected lateinit var mockMvc: MockMvc



    @Autowired
    protected lateinit var resourceLoader: ResourceLoader

    protected val classpath = "classpath:"

    @Before
    fun setUp(context: WebApplicationContext) {
        val delegatingFilterProxy = DelegatingFilterProxy()
        delegatingFilterProxy.init(MockFilterConfig(context.servletContext, BeanIds.SPRING_SECURITY_FILTER_CHAIN))
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .addFilters<DefaultMockMvcBuilder>(delegatingFilterProxy)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build()
    }
}

