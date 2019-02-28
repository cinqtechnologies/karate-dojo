package aero.sita.demo.karate.transform;

import aero.sita.demo.karate.dto.Greeting;
import aero.sita.demo.karate.entity.PersistedGreeting;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GreetTransformer {

  private final ModelMapper mapper;

  public Greeting transform(final PersistedGreeting persistedGreeting) {
    return mapper.map(persistedGreeting, Greeting.class);
  }

  public PersistedGreeting transform(Greeting greeting) {
    return mapper.map(greeting, PersistedGreeting.class);
  }
}
