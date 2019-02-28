package aero.sita.demo.karate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Greeting {

  private String name;

  @JsonProperty("lastHandshake")
  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  private ZonedDateTime lastUpdated;

  @JsonProperty("greeting")
  private String fromStoredProcedure;

}
