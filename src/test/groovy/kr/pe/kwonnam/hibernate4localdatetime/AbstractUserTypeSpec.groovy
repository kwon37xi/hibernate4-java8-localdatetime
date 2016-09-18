package kr.pe.kwonnam.hibernate4localdatetime

import kr.pe.kwonnam.hibernate4localdatetime.entities.Article
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.service.ServiceRegistryBuilder
import spock.lang.Specification

/**
 * Hibernate/JPA testing specification.
 */
abstract class AbstractUserTypeSpec extends Specification {

    Configuration configuration;

    SessionFactory sf;

    void setup() {
        configuration = new Configuration();

        configuration.addAnnotatedClass(Article)

        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.format_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        // hibernate 4.2.x 미만 의 ServiceRegistryBuilder 설정.
        // 4.x 끼리도 버전마다 조금씩 달라질 수 있다.
        def srBuilder = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
        def serviceRegistry = srBuilder.buildServiceRegistry()

        sf = configuration.buildSessionFactory(serviceRegistry);
    }

    void cleanup() {
        sf.close()
    }
}
