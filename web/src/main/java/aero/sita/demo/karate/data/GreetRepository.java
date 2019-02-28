package aero.sita.demo.karate.data;

import aero.sita.demo.karate.entity.PersistedGreeting;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetRepository extends CrudRepository<PersistedGreeting, Integer> {

  PersistedGreeting findByName(final String name);

  int deleteByName(String name);

  @Transactional
  @Procedure(name = "update_greeting")
  void updateGreeting(final @Param("personName") String personName);

}

//@Repository
//@NamedStoredProcedureQuery(
//    nam
//    e = "updateGreeting",
//    procedureName = "update_greeting",
//    parameters = @StoredProcedureParameter(name = "greeting", type = PersistedGreeting.class))
//
//public interface GreetRepository extends CrudRepository<PersistedGreeting, Integer> {
//
//  PersistedGreeting findByName(final String name);
//
//  boolean deleteByName(String name);
//
//  @Procedure(name = "updateGreeting")
//  void updateGreeting(final PersistedGreeting greeting);
//
//}
