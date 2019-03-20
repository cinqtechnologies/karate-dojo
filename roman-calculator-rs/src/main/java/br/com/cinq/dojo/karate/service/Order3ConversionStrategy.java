package br.com.cinq.dojo.karate.service;

import org.springframework.stereotype.Component;

@Component
public class Order3ConversionStrategy implements RomanNumberConversionStrategy {

  @Override
  public String toRoman(int number) {
    switch (number) {
      case 1: return "M";
      case 2: return "MM";
      case 3: return "MMM";
    }
    return "";

  }

  @Override
  public int ordinal() {
    return 3;
  }
}
