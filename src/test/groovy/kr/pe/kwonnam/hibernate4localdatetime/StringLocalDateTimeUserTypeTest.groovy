package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StringLocalDateTimeUserTypeTest extends Specification {
    StringLocalDateTimeUserType stringLocalDateTimeUserType = new StringLocalDateTimeUserType()

    Properties parameters = new Properties()

    def "setParameterValues format=null"() {
        given:
        parameters.remove(StringLocalDateTimeUserType.PARAM_PATTERN)

        when:
        stringLocalDateTimeUserType.setParameterValues(parameters)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == 'pattern must not be null.'
    }

    def "setParameterValues format = not null"() {
        given:
        parameters.setProperty(StringLocalDateTimeUserType.PARAM_PATTERN, "yyyy-MM-dd HH:mm:ss")
        LocalDateTime now = LocalDateTime.now()

        when:
        stringLocalDateTimeUserType.setParameterValues(parameters)

        then:
        stringLocalDateTimeUserType.getPattern() == 'yyyy-MM-dd HH:mm:ss'
        stringLocalDateTimeUserType.getFormatter().format(now) == DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(now)
    }

    def "sqlTypes"() {
        when:
        int[] sqlTypes = stringLocalDateTimeUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.VARCHAR
    }

    def "returnedClass"() {
        when:
        Class clazz = stringLocalDateTimeUserType.returnedClass()

        then:
        clazz == LocalDateTime
    }
}
