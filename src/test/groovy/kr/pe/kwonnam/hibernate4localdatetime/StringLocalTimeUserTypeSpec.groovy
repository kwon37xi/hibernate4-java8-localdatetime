package kr.pe.kwonnam.hibernate4localdatetime

import spock.lang.Specification

import java.sql.Types
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class StringLocalTimeUserTypeSpec extends Specification {

    StringLocalTimeUserType stringLocalTimeUserType = new StringLocalTimeUserType()


    Properties parameters = new Properties()

    def "setParameterValues parameters=null"() {
        given:
        parameters = null;

        when:
        stringLocalTimeUserType.setParameterValues(parameters)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == 'parameters must not be null.'

    }

    def "setParameterValues format=null"() {
        given:
        parameters.remove(StringLocalTimeUserType.PARAM_PATTERN)

        when:
        stringLocalTimeUserType.setParameterValues(parameters)

        then:
        IllegalArgumentException ex = thrown()
        ex.getMessage() == 'pattern must not be null.'
    }

    def "setParameterValues format = not null"() {
        given:
        parameters.setProperty(StringLocalTimeUserType.PARAM_PATTERN, "HH:mm:ss")
        LocalTime now = LocalTime.now()

        when:
        stringLocalTimeUserType.setParameterValues(parameters)

        then:
        stringLocalTimeUserType.getPattern() == 'HH:mm:ss'
        stringLocalTimeUserType.getFormatter().format(now) == DateTimeFormatter.ofPattern("HH:mm:ss").format(now)
    }

    def "sqlTypes"() {
        when:
        int[] sqlTypes = stringLocalTimeUserType.sqlTypes()

        then:
        sqlTypes.length == 1
        sqlTypes[0] == Types.VARCHAR
    }

    def "returnedClass"() {
        when:
        Class clazz = stringLocalTimeUserType.returnedClass()

        then:
        clazz == LocalTime
    }
}
