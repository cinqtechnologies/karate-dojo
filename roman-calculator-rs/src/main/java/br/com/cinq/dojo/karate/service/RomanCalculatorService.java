package br.com.cinq.dojo.karate.service;

import br.com.cinq.dojo.karate.dto.Operation;
import br.com.cinq.dojo.karate.dto.Symbol;
import br.com.cinq.dojo.karate.exception.RomanCalcException;
import br.com.cinq.dojo.karate.response.MathError;
import br.com.cinq.dojo.karate.response.MathResult;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RomanCalculatorService {

  private final RomanMathUtil math;

  public MathResult calculate(Operation operation) {
    final Symbol symbol = operation.getSymbol();
    final String first = operation.getFirst();
    final String second = operation.getSecond();

    final int a = math.convertToNumber(first);
    final int b = math.convertToNumber(second);

    int result = 0;

    switch(symbol) {
      case sum: {
        result = a + b;
        break;
      }
      case subtract: {
        result = a - b;
        break;
      }
      case divide: {
        result = a / b;
        break;
      }
      case multiply: {
        result = a * b;
      }
    }

    if(result > 3999) {
      return MathResult.builder()
          .operation(operation)
          .success(false)
          .errors(Collections.singletonList(new MathError("Roman numbers overflow")))
          .build();
    }

    final String romanResult = math.convertToRoman(result);

    return MathResult.builder()
        .success(true)
        .result(romanResult)
        .operation(operation)
        .build();
  }
}