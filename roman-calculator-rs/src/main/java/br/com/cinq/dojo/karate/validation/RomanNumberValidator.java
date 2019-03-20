package br.com.cinq.dojo.karate.validation;

import br.com.cinq.dojo.karate.service.RomanMathUtil;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class RomanNumberValidator implements ConstraintValidator<ValidRomanNumber, String> {

   @Autowired
   private RomanMathUtil math;

   public void initialize(ValidRomanNumber constraint) {
   }

   public boolean isValid(String romanNumber, ConstraintValidatorContext context) {
      return math.isValidRoman(romanNumber);
   }
}
