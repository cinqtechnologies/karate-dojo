package br.com.cinq.dojo.karate.service;

import org.springframework.stereotype.Component;

@Component
public class Order0ConversionStrategy implements RomanNumberConversionStrategy {

  @Override
  public String toRoman(int number) {

    switch (number) {
      case 1: return "I";
      case 2: return "II";
      case 3: return "III";
      case 4: return "IV";
      case 5: return "V";
      case 6: return "VI";
      case 7: return "VII";
      case 8: return "VIII";
      case 9: return "IX";
    }
    return "";
  }

  @Override
  public int ordinal() {
    return 0;
  }
}
