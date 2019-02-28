package aero.sita.demo.karate.controller;

import aero.sita.demo.karate.dto.Greeting;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class GreetingResourceAssembler implements ResourceAssembler<Greeting, Resource<Greeting>> {

  @Override
  public Resource<Greeting> toResource(Greeting greeting) {
    return new Resource<>(greeting,
        linkTo(methodOn(GreetController.class).handshake(greeting.getName())).withSelfRel());
  }
}