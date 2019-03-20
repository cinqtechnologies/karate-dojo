package br.com.cinq.dojo.karate.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/ping")
public class PingController {

  @RequestMapping
  public ResponseEntity<String> ping() {
    return ResponseEntity.ok("PONG");
  }
}