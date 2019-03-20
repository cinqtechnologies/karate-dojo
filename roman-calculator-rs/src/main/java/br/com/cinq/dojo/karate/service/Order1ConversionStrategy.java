package br.com.cinq.dojo.karate.service;

import org.springframework.stereotype.Component;

@Component
public class Order1ConversionStrategy implements RomanNumberConversionStrategy {

  @Override
  public String toRoman(int number) {
    switch (number) {
      case 1: return "X";
      case 2: return "XX";
      case 3: return "XXX";
      case 4: return "XL";
      case 5: return "L";
      case 6: return "LX";
      case 7: return "LXX";
      case 8: return "LXXX";
      case 9: return "XC";
    }
    return "";

  }

  @Override
  public int ordinal() {
    return 1;
  }
}
