package br.com.cinq.dojo.karate.validation;

import br.com.cinq.dojo.karate.dto.Symbol;
import br.com.cinq.dojo.karate.service.RomanMathUtil;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class MathSymbolValidator implements ConstraintValidator<ValidSymbol, String> {

   public void initialize(ValidSymbol constraint) {
   }

   public boolean isValid(String symbol, ConstraintValidatorContext context) {
      return symbol != null &&
          Arrays.stream(Symbol.values())
              .map(Symbol::toString)
              .anyMatch(symbol.toUpperCase()::equalsIgnoreCase);
   }
}
