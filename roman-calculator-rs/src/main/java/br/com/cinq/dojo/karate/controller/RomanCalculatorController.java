package br.com.cinq.dojo.karate.controller;

import br.com.cinq.dojo.karate.dto.Operation;
import br.com.cinq.dojo.karate.dto.Symbol;
import br.com.cinq.dojo.karate.exception.RomanCalcException;
import br.com.cinq.dojo.karate.response.MathError;
import br.com.cinq.dojo.karate.response.MathResult;
import br.com.cinq.dojo.karate.service.RomanCalculatorService;
import br.com.cinq.dojo.karate.validation.ValidRomanNumber;
import br.com.cinq.dojo.karate.validation.ValidSymbol;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Validated
@RestController
@ControllerAdvice
@AllArgsConstructor
@RequestMapping(path = "/calc")
public class RomanCalculatorController extends ResponseEntityExceptionHandler {

  private final RomanCalculatorService service;

  @RequestMapping(
      path = "/{first}/{operation}/{second}",
      method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<MathResult> doMath(
      final @Valid @ValidRomanNumber @PathVariable("first") String first,
      final @Valid @ValidSymbol @PathVariable("operation") String symbol,
      final @Valid @ValidRomanNumber @PathVariable("second") String second) {

    final Operation operation = new Operation();
    operation.setFirst(first);
    operation.setSymbol(Symbol.valueOf(symbol));
    operation.setSecond(second);

    return Optional.ofNullable(service.calculate(operation))
        .map(result -> result.isSuccess()? ResponseEntity.ok(result): ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result))
        .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<MathResult> handleConstraintViolationException(
      final ConstraintViolationException exception) {
    return ResponseEntity.unprocessableEntity().body(buildErrors(exception));
  }

  private MathResult buildErrors(ConstraintViolationException exception) {
    final List<MathError> errors = exception.getConstraintViolations()
        .stream()
        .map(violation -> new MathError(violation.getMessage()))
        .collect(Collectors.toList());
    return MathResult.builder()
        .errors(errors)
        .build();
  }

  @ExceptionHandler(RomanCalcException.class)
  public ResponseEntity<MathResult> handleConstraintViolationException(
      final RomanCalcException exception) {
    return ResponseEntity.unprocessableEntity().body(buildError(exception));
  }

  private MathResult buildError(RomanCalcException exception) {
    return MathResult.builder()
        .errors(Collections.singletonList(new MathError(exception.getMessage())))
        .build();
  }
}
