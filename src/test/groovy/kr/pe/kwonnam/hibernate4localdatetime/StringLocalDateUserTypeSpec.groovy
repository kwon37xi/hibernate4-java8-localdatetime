package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class StringLocalDateUserTypeSpec extends Specification {
    StringLocalDateUserType stringLocalDateUserType = new StringLocalDateUserType()

    Properties parameters = new Properties()

    def "setParameterValues parameters=null"() {
        given:
        parameters = null;

        when:
        stringLocalDateUserType.setParameterValues(parameters)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == 'parameters must not be null.'

    }

    def "setParameterValues format=null"() {
        given:
        parameters.remove(StringLocalDateUserType.PARAM_PATTERN)

        when:
        stringLocalDateUserType.setParameterValues(parameters)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == 'pattern must not be null.'
    }

    def "setParameterValues format = not null"() {
        given:
        parameters.setProperty(StringLocalDateUserType.PARAM_PATTERN, "yyyy-MM-dd")
        LocalDateTime now = LocalDateTime.now()

        when:
        stringLocalDateUserType.setParameterValues(parameters)

        then:
        stringLocalDateUserType.getPattern() == 'yyyy-MM-dd'
        stringLocalDateUserType.getFormatter().format(now) == DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now)
    }

    def "sqlTypes"() {
        when:
        int[] sqlTypes = stringLocalDateUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.VARCHAR
    }

    def "returnedClass"() {
        when:
        Class clazz = stringLocalDateUserType.returnedClass()

        then:
        clazz == LocalDate
    }
}
