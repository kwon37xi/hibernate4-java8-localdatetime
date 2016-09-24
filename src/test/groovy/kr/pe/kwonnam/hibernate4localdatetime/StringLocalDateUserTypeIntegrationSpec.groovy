package kr.pe.kwonnam.hibernate4localdatetime

import groovy.sql.Sql
import kr.pe.kwonnam.hibernate4localdatetime.entities.StringLocalDateEntity
import org.hibernate.cfg.Configuration
import org.hibernate.jdbc.Work

import java.sql.Connection
import java.time.LocalDate

class StringLocalDateUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {
    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(StringLocalDateEntity)
    }

    def "save and get"() {
        given:
        StringLocalDateEntity entity = new StringLocalDateEntity()
        entity.title = 'StringLocalDate test'
        entity.createdDate = LocalDate.of(2016, 9, 21)
        entity.updatedDate = LocalDate.of(1977, 4, 29)

        when:
        Long id = session.save(entity)
        session.evict(entity)
        StringLocalDateEntity readFromDb = session.get(StringLocalDateEntity, 1L)

        then:
        id == 1L

        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            def row = sql.firstRow("select created_date, updated_date from string_local_date_entities where id = 1")
            assert row.created_date == '20160921'
            assert row.updated_date == '1977/04/29'
        } as Work)

        readFromDb.id == 1L
        readFromDb.createdDate == LocalDate.of(2016, 9, 21)
        readFromDb.updatedDate == LocalDate.of(1977, 4, 29)
        readFromDb.title == 'StringLocalDate test'
    }

    def "get null or empty String"() {
        given:
        session.doWork({ Connection con ->
            Sql sql = new Sql(con)
            sql.executeInsert("""
                INSERT INTO string_local_date_entities
                    (id, title, created_date, updated_date)
                VALUES(1, 'null/empty test', '', null)
            """)
        } as Work)

        when:
        StringLocalDateEntity readFromDb = session.get(StringLocalDateEntity, 1L)

        then:
        readFromDb.id == 1L
        readFromDb.createdDate == null
        readFromDb.updatedDate == null
        readFromDb.title == 'null/empty test'
    }
}
