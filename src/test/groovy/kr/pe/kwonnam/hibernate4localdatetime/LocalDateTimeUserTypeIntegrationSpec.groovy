package kr.pe.kwonnam.hibernate4localdatetime

import groovy.sql.Sql
import kr.pe.kwonnam.hibernate4localdatetime.entities.LocalDateTimeEntity
import org.hibernate.cfg.Configuration
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.time.LocalDateTime

class LocalDateTimeUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {

    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(LocalDateTimeEntity)
    }

    def "save and get"() {
        given:
        final LocalDateTimeEntity entity = new LocalDateTimeEntity()
        entity.title = 'entity title'
        entity.createdAt = LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123456789)
        entity.updatedAt = null

        when:
        Long id = session.save(entity)
        session.evict(entity)
        LocalDateTimeEntity readFromDb = session.get(LocalDateTimeEntity, 1L)

        then:
        id == 1L
        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            def row = sql.firstRow("select * from local_date_time_entities")
            println "raw query createdAt result ${row.created_at}" // check for nano second truncated
        } as Work)

        readFromDb.id == 1L
        readFromDb.createdAt !=  LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123456789)
        readFromDb.createdAt == LocalDateTime.of(2016, 9, 5, 15, 40, 21, 123000000) // nanoseconds truncated
        readFromDb.updatedAt == null
        readFromDb.title == entity.title
    }
}
