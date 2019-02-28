package aero.sita.demo.karate.service;

import aero.sita.demo.karate.data.GreetRepository;
import aero.sita.demo.karate.dto.Greeting;
import aero.sita.demo.karate.entity.PersistedGreeting;
import aero.sita.demo.karate.transform.GreetTransformer;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GreetService {

  private final GreetRepository repository;

  private final GreetTransformer transformer;

  @Transactional
  public Optional<Greeting> findGreetingByPerson(String name) {
    PersistedGreeting persistedGreeting = repository.findByName(name);
    if (persistedGreeting != null) {
      repository.updateGreeting(name);
      return Optional.of(transformer.transform(repository.findByName(name)));
    }
    return Optional.empty();
  }

  @Transactional
  public Optional<Greeting> tryCreating(Greeting greeting) {
    if (repository.findByName(greeting.getName()) != null) {
      return Optional.empty();
    }
    final PersistedGreeting persisting = transformer.transform(greeting);
    return Optional.ofNullable(transformer.transform(repository.save(persisting)));
  }

  @Transactional
  public boolean tryDeleting(String name) {
    if (repository.findByName(name) == null) {
      return false;
    }
    return repository.deleteByName(name) >= 1;
  }
}