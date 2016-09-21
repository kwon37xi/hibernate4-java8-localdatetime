package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.LocalTimeEntity
import org.hibernate.cfg.Configuration

import java.time.LocalTime

class LocalTimeUserTypeIntegrationSpec extends AbstractUserTypeIntegrationSpec {

    @Override
    void addAnnotatedClass(Configuration configuration) {
        configuration.addAnnotatedClass(LocalTimeEntity)
    }

    def "save and get"() {
        given:
        LocalTimeEntity entity = new LocalTimeEntity()
        entity.title = 'local_time test'
        entity.createdTime = LocalTime.of(13, 21, 59, 123456789)
        entity.updatedTime = null;

        when:
        Long id = session.save(entity)
        session.evict(entity)
        LocalTimeEntity readFromDb = session.get(LocalTimeEntity, 1L)

        then:
        id == 1L
        readFromDb.id == 1L
        readFromDb.createdTime == LocalTime.of(13, 21, 59);
        readFromDb.updatedTime == null
    }
}
