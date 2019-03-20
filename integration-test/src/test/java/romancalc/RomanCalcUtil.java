package romancalc;

import java.util.Arrays;
import java.util.List;

public class RomanCalcUtil {


  private static final List<String> romans = Arrays.asList("i", "I", "v", "V", "x", "X", "l", "L", "Q", "c", "C", "d", "D", "m", "M", "N");

  public static final String removeNonRomans(String value) {
    final StringBuilder result = new StringBuilder();

    for(char aChar: value.toCharArray()) {
      final String item = Character.toString(aChar);
      if(romans.contains(item)) {
        result.append(item);
      }
    }

    return result.toString();
  }
}
