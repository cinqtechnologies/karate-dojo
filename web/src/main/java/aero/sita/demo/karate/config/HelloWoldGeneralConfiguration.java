package aero.sita.demo.karate.config;

import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class HelloWoldGeneralConfiguration {

  @Bean
  public ModelMapper createModelMapper() {
    return new ModelMapper();
  }

  @Profile("prod")
  @Bean(name = "emf")
  public LocalContainerEntityManagerFactoryBean createPRODEntityManagerFactory(final DataSource dataSource) {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(false);
    vendorAdapter.setShowSql(false);

    return createLocalContainerEntityManagerFactoryBean(dataSource, vendorAdapter);
  }

  @Profile("local")
  @Bean(name = "emf")
  public LocalContainerEntityManagerFactoryBean createLOCALEntityManagerFactory(final DataSource dataSource) {
    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setGenerateDdl(true);
    vendorAdapter.setShowSql(true);

    return createLocalContainerEntityManagerFactoryBean(dataSource, vendorAdapter);
  }

  private LocalContainerEntityManagerFactoryBean createLocalContainerEntityManagerFactoryBean(
      DataSource dataSource, HibernateJpaVendorAdapter vendorAdapter) {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(vendorAdapter);
    factory.setPersistenceUnitRootLocation(null);
    factory.setPackagesToScan("aero.sita.demo.karate");
    factory.setDataSource(dataSource);
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    properties.setProperty("hibernate.hbm2ddl.auto", "update");
    factory.setJpaProperties(properties);
    return factory;
  }

  @Bean(name = "trm")
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager txManager = new JpaTransactionManager();
    txManager.setEntityManagerFactory(entityManagerFactory);
    return txManager;
  }
}
