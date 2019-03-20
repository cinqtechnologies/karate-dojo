package br.com.cinq.dojo.karate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class RomanMathUtil {

  private List<String> alphaRomanNumbers = Arrays
      .asList("I", "i", "V", "v", "X", "x", "L", "l", "C", "c", "D", "d", "M", "m");

  private Map<String, Integer> mappedValues;


  private final List<RomanNumberConversionStrategy> strategies;

  public RomanMathUtil(final List<RomanNumberConversionStrategy> strategies) {
    this.strategies = strategies;
  }

  {
    mappedValues = new HashMap<>();
    mappedValues.put("M", 1000);
    mappedValues.put("D", 500);
    mappedValues.put("C", 100);
    mappedValues.put("L", 50);
    mappedValues.put("X", 10);
    mappedValues.put("V", 5);
    mappedValues.put("I", 1);
  }

  public boolean isValidRoman(final String value) {
    return value != null &&
        value.length() > 0 &&
        IntStream.range(0, value.length())
            .mapToObj(idx -> Character.toString(value.charAt(idx)))
            .allMatch(alphaRomanNumbers::contains) &&
        matchesRomanPattern(value.toUpperCase()) &&
        convertToNumber(value) <= 3999;
  }

  private boolean matchesRomanPattern(String value) {
    final String regex = "^(?=[MDCLXVI])M{0,3}(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$";
    return value.matches(regex);
  }

  public int convertToNumber(final String value) {
    if (value == null || value.length() < 1) {
      return 0;
    }
    final List<String> grouped = new ArrayList<>();
    StringBuilder partialGroup = new StringBuilder();
    char current = value.charAt(0);
    for(char charInArray: value.toCharArray()) {
      if(charInArray == current || mappedValues.get(Character.toString(current)) < mappedValues.get(Character.toString(charInArray))) {
        partialGroup.append(charInArray);
        current = charInArray;
      } else {
        grouped.add(partialGroup.toString());
        partialGroup = new StringBuilder(Character.toString(charInArray));
        current = charInArray;
      }
    }
    grouped.add(partialGroup.toString());
    return grouped.stream()
        .map(this::toNumber)
        .reduce(0, (a, b) -> a+b);
  }

  private Integer toNumber(String value) {
    int result = 0;
    for(int i = 0; i < value.length() - 1; i++) {
      int a = mappedValues.get(Character.toString(value.charAt(i)));
      int b = mappedValues.get(Character.toString(value.charAt(i+1)));
      if (a < b) {
        result -= a;
      } else {
        result += a;
      }
    }
    result += mappedValues.get(Character.toString(value.charAt(value.length() - 1)));
    return result;
  }

  public String convertToRoman(int result) {
    List<Integer> numbers = new ArrayList<>();
    while (result > 0) {
      numbers.add(result % 10);
      result /= 10;
    }
    return convertToRoman(numbers);
  }

  private String convertToRoman(List<Integer> numbers) {
    return IntStream.iterate(numbers.size()-1, i -> i-1)
        .limit(numbers.size())
        .mapToObj(idx -> strategies.stream().filter(
            obj -> obj.ordinal() == idx).findAny().get().toRoman(numbers.get(idx)))
        .collect(Collectors.joining());
  }
}