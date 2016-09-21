package kr.pe.kwonnam.hibernate4localdatetime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalTime;
import java.util.Objects;

/**
 * <p>LocalTime Hibernate User Type.</p>
 *
 * <p>
 * <em>Beware LocalTime supports nanoseconds, but nanoseconds will be truncated when it is converted to java.sql.Time.</em>
 * </p>
 *
 * @see java.time.LocalTime
 * @see java.sql.Time
 */
public class LocalTimeUserType implements EnhancedUserType, Serializable {
    private static final int SQL_TYPE = Types.TIME;

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
        Time time = (Time) StandardBasicTypes.TIME.nullSafeGet(rs, names, session, owner);

        if (time == null) {
            return null;
        }

        return time.toLocalTime();
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.TIME.nullSafeSet(st, null, index, session);
            return;
        }

        LocalTime localTime = (LocalTime) value;
        Time time = Time.valueOf(localTime);

        StandardBasicTypes.TIME.nullSafeSet(st, time, index, session);
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
