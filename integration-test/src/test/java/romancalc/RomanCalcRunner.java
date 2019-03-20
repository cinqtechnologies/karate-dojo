package romancalc;

import com.intuit.karate.junit5.Karate;

public class RomanCalcRunner {

  @Karate.Test
  Karate testRomanCalculator() {
    return new Karate().feature("romancalc").relativeTo(getClass());
  }
}
