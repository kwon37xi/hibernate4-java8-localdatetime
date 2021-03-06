package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.LocalDateEntity
import org.hibernate.cfg.Configuration

import java.time.LocalDate
import java.time.Month

class LocalDateUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {
    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(LocalDateEntity)
    }

    def "save and get"() {
        given:
        LocalDateEntity entity = new LocalDateEntity()
        entity.title = 'local_date test'
        entity.createdDate = LocalDate.of(2016, Month.SEPTEMBER, 18)
        entity.updatedDate = null

        when:
        Long id = session.save(entity)
        session.evict(entity)
        LocalDateEntity readFromDb = session.get(LocalDateEntity, 1L)

        then:
        id == 1L
        readFromDb.id == 1L
        readFromDb.createdDate == LocalDate.of(2016, Month.SEPTEMBER, 18);
        readFromDb.updatedDate == null
        readFromDb.title == 'local_date test'
    }
}
