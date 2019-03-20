package br.com.cinq.dojo.karate.service;

public interface RomanNumberConversionStrategy {

  String toRoman(final int number);

  default int ordinal() {
    return -1;
  }
}
