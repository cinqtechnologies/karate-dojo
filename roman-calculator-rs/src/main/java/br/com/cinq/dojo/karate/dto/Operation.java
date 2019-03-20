package br.com.cinq.dojo.karate.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation {

  private Symbol symbol;

  private String first;

  private String second;
}