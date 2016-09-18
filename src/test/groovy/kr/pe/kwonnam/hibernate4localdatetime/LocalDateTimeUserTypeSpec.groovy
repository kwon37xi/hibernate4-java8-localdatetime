package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.Article

import java.sql.Types
import java.time.LocalDateTime

class LocalDateTimeUserTypeSpec extends AbstractUserTypeIntegrationSpec {

    LocalDateTimeUserType localDateTimeUserType = new LocalDateTimeUserType()

    def "save and get"() {
        given:
        final LocalDateTime expectedLocalDateTime = LocalDateTime.of(2016, 9, 5, 15, 40, 21)

        final Article article = new Article()
        article.title = 'article title'
        article.content = 'article content'
        article.createdAt = expectedLocalDateTime
        article.updatedAt = null

        when:
        Long id = session.save(article)
        session.evict(article)
        Article readFromDb = session.get(Article, 1L)

        then:
        assert id == 1L

        readFromDb.id == 1L
        readFromDb.createdAt == expectedLocalDateTime
        readFromDb.updatedAt == null
        readFromDb.title == article.title
        readFromDb.content == article.content
    }

    def "sqlTypes"() {
        when:
        int[] sqlTypes = localDateTimeUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.TIMESTAMP
    }

    def "returnedClass"() {
        when:
        Class clazz = localDateTimeUserType.returnedClass()

        then:
        clazz == LocalDateTime
    }
}
