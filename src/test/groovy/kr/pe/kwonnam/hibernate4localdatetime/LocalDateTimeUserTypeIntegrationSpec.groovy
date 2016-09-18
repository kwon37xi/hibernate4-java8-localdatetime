package kr.pe.kwonnam.hibernate4localdatetime

import groovy.sql.Sql
import kr.pe.kwonnam.hibernate4localdatetime.entities.Article
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.time.LocalDateTime

class LocalDateTimeUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {

    def "save and get"() {
        given:
        final LocalDateTime expectedLocalDateTime = LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123456789)

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
        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            def row = sql.firstRow("select * from articles")
            println "raw query createdAt result ${row.createdAt}" // check for nano second truncated
        } as Work)

        readFromDb.id == 1L
        readFromDb.createdAt != expectedLocalDateTime
        readFromDb.createdAt == LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123000000) // nanoseconds truncated
        readFromDb.updatedAt == null
        readFromDb.title == article.title
        readFromDb.content == article.content
    }
}
