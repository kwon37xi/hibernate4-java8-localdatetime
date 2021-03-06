package kr.pe.kwonnam.hibernate4localdatetime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

public class StringLocalTimeUserType implements EnhancedUserType, ParameterizedType {
    /**
     * Date format pattern parameter name
     **/
    public static final String PARAM_PATTERN = "pattern";

    public static final int SQL_TYPE = Types.VARCHAR;

    /**
     * Date format pattern
     **/
    private String pattern;

    private DateTimeFormatter formatter;

    public String getPattern() {
        return pattern;
    }

    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("parameters must not be null.");
        }

        pattern = parameters.getProperty(PARAM_PATTERN, null);

        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null.");
        }

        formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{SQL_TYPE};
    }

    @Override
    public Class returnedClass() {
        return LocalTime.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        String timeString = (String) StandardBasicTypes.STRING.nullSafeGet(rs, names, session, owner);

        if (timeString == null || timeString.isEmpty()) {
            return null;
        }

        return LocalTime.parse(timeString, formatter);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.STRING.nullSafeSet(st, null, index, session);
            return;
        }

        LocalTime localTime = (LocalTime) value;
        String timeString = localTime.format(formatter);
        StandardBasicTypes.STRING.nullSafeSet(st, timeString, index, session);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        // no need to deepCopy immutable objects
        return value;
    }

    @Override
    public boolean isMutable() {
        // LocalTime is immutable
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        // no need to replace immutable objects
        return original;
    }

    @Override
    public String objectToSQLString(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toXMLString(Object value) {
        return Objects.toString(value);
    }

    @Override
    public Object fromXMLString(String xmlValue) {
        return LocalTime.parse(xmlValue);
    }
}
