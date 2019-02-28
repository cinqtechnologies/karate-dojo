package aero.sita.demo.karate.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.StoredProcedureParameter;
import lombok.Data;

@Data
@Entity(name = "greeting")
@NamedStoredProcedureQuery(
    name = "update_greeting",
    procedureName = "update_greeting",
    parameters = @StoredProcedureParameter(name = "personName", type = String.class))
public class PersistedGreeting implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;

  private ZonedDateTime lastUpdated;

  private String fromStoredProcedure;

  private String greet;

}
