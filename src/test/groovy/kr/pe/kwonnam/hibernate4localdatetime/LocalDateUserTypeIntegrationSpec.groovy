package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.LocalDateEntity

import java.time.LocalDate
import java.time.Month


class LocalDateUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {

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
        assert id == 1L
        readFromDb.id == 1L
        readFromDb.createdDate == LocalDate.of(2016, Month.SEPTEMBER, 18);
        readFromDb.updatedDate == null
        readFromDb.title == 'local_date test'
    }
}
