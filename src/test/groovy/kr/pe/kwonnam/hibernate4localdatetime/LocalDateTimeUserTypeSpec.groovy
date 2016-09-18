package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.Article

import java.time.LocalDateTime

class LocalDateTimeUserTypeSpec extends AbstractUserTypeSpec {
    def "save"() {
        given:
        def session = sf.openSession()

        Article article = new Article()
        article.title = 'article title'
        article.content = 'article content'
        article.createdAt = LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123)

        when:
        session.save(article)

        then:
        true
    }
}
