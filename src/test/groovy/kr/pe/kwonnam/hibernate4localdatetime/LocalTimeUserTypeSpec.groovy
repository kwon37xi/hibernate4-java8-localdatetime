package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalTime


class LocalTimeUserTypeSpec extends Specification {
    LocalTimeUserType localTimeUserType = new LocalTimeUserType()

    def "sqlTypes"() {
        when:
        int[] sqlTypes = localTimeUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.TIME
    }

    def "returnedClass"() {
        when:
        Class clazz = localTimeUserType.returnedClass()

        then:
        clazz == LocalTime
    }
}
