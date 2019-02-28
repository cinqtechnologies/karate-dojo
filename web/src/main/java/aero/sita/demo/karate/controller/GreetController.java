package aero.sita.demo.karate.controller;

import aero.sita.demo.karate.dto.Greeting;
import aero.sita.demo.karate.service.GreetService;
import java.net.URI;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/greet")
public class GreetController {

  private final GreetService service;

  private final ResourceAssembler<Greeting, Resource<Greeting>> assembler;

  @RequestMapping(path = "{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Greeting> handshake(final @PathVariable(name = "name") String name) {
    final Optional<Greeting> greeting = service.findGreetingByPerson(name);

    return greeting.map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.notFound()::build);
  }

  @RequestMapping(
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Greeting> greetingsFrom(final @RequestBody Greeting greeting) {
    Optional<Greeting> result = service.tryCreating(greeting);
    return result.map(mapped -> ResponseEntity
        .created(URI.create(assembler.toResource(mapped).getId().expand().getHref())))
        .orElseGet(ResponseEntity::unprocessableEntity)
        .build();
  }

  @RequestMapping(
      path = "{name}",
      method = RequestMethod.DELETE)
  public ResponseEntity<?> greetingsFrom(final @PathVariable(name = "name") String name) {
    Optional<Boolean> result = Optional.of(service.tryDeleting(name));
    return result.filter(Boolean.TRUE::equals)
        .map($_ -> ResponseEntity.noContent().build())
        .orElseGet(ResponseEntity.notFound()::build);
  }
}
