package kr.pe.kwonnam.hibernate4localdatetime

import groovy.sql.Sql
import kr.pe.kwonnam.hibernate4localdatetime.entities.StringLocalTimeEntity
import org.hibernate.cfg.Configuration
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.time.LocalTime

class StringLocalTimeUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {
    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(StringLocalTimeEntity)
    }

    def "save and get"() {
        given:
        StringLocalTimeEntity entity = new StringLocalTimeEntity()
        entity.title = 'StringLocalTime test'
        entity.createdTime = LocalTime.of(11, 31, 22, 987654321)
        entity.updatedTime = LocalTime.of(21, 54, 59, 999999998)

        when:
        Long id = session.save(entity)
        session.evict(entity)
        StringLocalTimeEntity readFromDb = session.get(StringLocalTimeEntity, 1L)

        then:
        id == 1L

        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            def row = sql.firstRow("select created_time, updated_time from string_local_time_entities where id = 1")
            assert row.created_time == '11:31:22.987654321'
            assert row.updated_time == '21-54-59-999999998'
        } as Work)

        readFromDb.id == 1L
        readFromDb.createdTime == LocalTime.of(11, 31, 22, 987654321)
        readFromDb.updatedTime == LocalTime.of(21, 54, 59, 999999998)
        readFromDb.title == 'StringLocalTime test'
    }

    def "get null or empty String"() {
        given:
        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            sql.executeInsert("""
                INSERT INTO string_local_time_entities (id, title, created_time, updated_time)
                VALUES(1, 'null/empty test', '', null)
            """)
        } as Work)

        when:
        StringLocalTimeEntity readFromDb = session.get(StringLocalTimeEntity, 1L)

        then:
        readFromDb.id == 1L
        readFromDb.createdTime == null
        readFromDb.updatedTime == null
    }
}
