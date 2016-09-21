package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalDate

class LocalDateUserTypeSpec extends Specification {
    LocalDateUserType localDateUserType = new LocalDateUserType();


    def "sqlTypes"() {
        when:
        int[] sqlTypes = localDateUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.DATE
    }

    def "returnedClass"() {
        when:
        Class clazz = localDateUserType.returnedClass()

        then:
        clazz == LocalDate
    }
}
