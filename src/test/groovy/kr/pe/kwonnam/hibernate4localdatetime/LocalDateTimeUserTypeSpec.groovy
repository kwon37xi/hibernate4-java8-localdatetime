package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalDateTime


class LocalDateTimeUserTypeSpec extends Specification {
    LocalDateTimeUserType localDateTimeUserType = new LocalDateTimeUserType()

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
