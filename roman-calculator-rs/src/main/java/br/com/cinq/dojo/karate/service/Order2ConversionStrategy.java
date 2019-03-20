package br.com.cinq.dojo.karate.service;

import org.springframework.stereotype.Component;

@Component
public class Order2ConversionStrategy implements RomanNumberConversionStrategy {

  @Override
  public String toRoman(int number) {
    switch (number) {
      case 1: return "C";
      case 2: return "CC";
      case 3: return "CCC";
      case 4: return "CD";
      case 5: return "D";
      case 6: return "DC";
      case 7: return "DCC";
      case 8: return "DCCC";
      case 9: return "CM";
    }
    return "";

  }

  @Override
  public int ordinal() {
    return 2;
  }
}
