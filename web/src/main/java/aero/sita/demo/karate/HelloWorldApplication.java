package aero.sita.demo.karate;


import aero.sita.demo.karate.data.GreetRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackageClasses = GreetRepository.class,
    entityManagerFactoryRef = "emf",
    transactionManagerRef = "trm")
public class HelloWorldApplication {

  public static void main(String... args) {
    SpringApplication.run(HelloWorldApplication.class, args);
  }
}
