package kr.pe.kwonnam.hibernate4localdatetime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.jboss.logging.Logger;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

public class LocalDateTimeUserType implements EnhancedUserType, Serializable {
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, LocalDateTimeUserType.class.getName());

    private static final int SQL_TYPE = Types.TIMESTAMP;

    @Override
    public int[] sqlTypes() {
        return new int[] { SQL_TYPE };
    }

    @Override
    public Class returnedClass() {
        return LocalDateTime.class;
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
        final String columName = names[0];

        Date timestamp = (Date) StandardBasicTypes.TIMESTAMP.nullSafeGet(rs, names, session, owner);

        if (timestamp == null) {
            if (LOG.isTraceEnabled()) {
                LOG.tracev("Returning null as column {0}", columName);
            }
            return null;
        }

        LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault());

        if (LOG.isTraceEnabled()) {
            LOG.tracev("Returning '{0}' as column {1}", localDateTime, columName);
        }
        return localDateTime;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            if (LOG.isTraceEnabled()) {
                LOG.tracev("Binding null to parameter: {0}", index);
            }
            StandardBasicTypes.TIMESTAMP.nullSafeSet(st, null, index, session);
            return;
        }

        LocalDateTime localDateTime = (LocalDateTime) value;
        Date timestamp = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        if (LOG.isTraceEnabled()) {
            LOG.tracev("Binding '{0}' to parameter: {1}", timestamp, index);
        }

        StandardBasicTypes.TIMESTAMP.nullSafeSet(st, timestamp, index, session);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        // no need to deepCopy immutable objects
        return value;
    }

    @Override
    public boolean isMutable() {
        // LocalDateTime is immutable
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
        return LocalDateTime.parse(xmlValue);
    }
}
