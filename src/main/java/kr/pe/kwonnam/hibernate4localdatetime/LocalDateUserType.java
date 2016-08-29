package kr.pe.kwonnam.hibernate4localdatetime;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.sql.Date;

public class LocalDateUserType implements EnhancedUserType, Serializable {
    private static final CoreMessageLogger LOG = Logger.getMessageLogger(CoreMessageLogger.class, LocalDateUserType.class.getName());

    private static final int SQL_TYPE = Types.DATE;

    @Override
    public int[] sqlTypes() {
        return new int[] { SQL_TYPE };
    }

    @Override
    public Class returnedClass() {
        return LocalDate.class;
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

        Date date = (Date) StandardBasicTypes.DATE.nullSafeGet(rs, names, session, owner);

        if (date == null) {
            if (LOG.isTraceEnabled()) {
                LOG.tracev("Returning null as column {0}", columName);
            }
            return null;
        }

        LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());

        if (LOG.isTraceEnabled()) {
            LOG.tracev("Returning '{0}' as column {1}", localDate, columName);
        }
        return localDate;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            if (LOG.isTraceEnabled()) {
                LOG.tracev("Binding null to parameter: {0}", index);
            }
            StandardBasicTypes.DATE.nullSafeSet(st, null, index, session);
            return;
        }

        LocalDate localDate = (LocalDate) value;
        Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());

        if (LOG.isTraceEnabled()) {
            LOG.tracev("Binding '{0}' to parameter: {1}", date, index);
        }

        StandardBasicTypes.DATE.nullSafeSet(st, date, index, session);
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        // no need to deepCopy immutable objects
        return value;
    }

    @Override
    public boolean isMutable() {
        // LocalDate is immutable
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
        return LocalDate.parse(xmlValue);
    }
}
