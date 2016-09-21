package kr.pe.kwonnam.hibernate4localdatetime

import groovy.sql.Sql
import kr.pe.kwonnam.hibernate4localdatetime.entities.StringLocalDateTimeEntity
import org.hibernate.cfg.Configuration
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.time.LocalDateTime

class StringLocalDateTimeUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {
    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(StringLocalDateTimeEntity)
    }

    def "save and get"() {
        given:
        StringLocalDateTimeEntity entity = new StringLocalDateTimeEntity()
        entity.title = 'StringLocalDateTime test'
        entity.createdAt = LocalDateTime.of(2016, 9, 21, 23, 54, 19, 987654321)
        entity.updatedAt = LocalDateTime.of(1977, 4, 29, 23, 30, 17, 987654321)

        when:
        Long id = session.save(entity)
        session.evict(entity)
        StringLocalDateTimeEntity readFromDb = session.get(StringLocalDateTimeEntity, 1L)


        then:
        id == 1L

        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            sql.with {
                def row = sql.firstRow("select created_at, updated_at from string_local_date_time_entities where id = 1")
                assert row.created_at == '20160921235419'
                assert row.updated_at == '1977/04/29 23:30:17.987'
            }
        } as Work)

        readFromDb.id == 1L
        readFromDb.createdAt == LocalDateTime.of(2016, 9, 21, 23, 54, 19)
        readFromDb.updatedAt == LocalDateTime.of(1977, 4, 29, 23, 30, 17, 987000000) // nanoseconds truncated
        readFromDb.title == 'StringLocalDateTime test'
    }

    def "get null or empty String"() {
        given:
        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            sql.with {
                sql.executeInsert("""
                    INSERT INTO string_local_date_time_entities
                        (id, title, created_at, updated_at)
                    VALUES(1, 'null/empty test', '', null)
""")
            }
        } as Work)

        when:
        StringLocalDateTimeEntity readFromDb = session.get(StringLocalDateTimeEntity, 1L);

        then:
        readFromDb.id == 1L
        readFromDb.createdAt == null
        readFromDb.updatedAt == null
        readFromDb.title == 'null/empty test'
    }
}
