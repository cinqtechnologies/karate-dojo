package br.com.cinq.dojo.karate.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RomanCalculatorConfiguration {

  @Bean
  public ModelMapper createModelMapper() {
    return new ModelMapper();
  }

}
