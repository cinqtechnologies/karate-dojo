package greeting;

import com.intuit.karate.junit5.Karate;

public class GreetingRunner {

  @Karate.Test
  Karate testGreetingService() {
    return new Karate().feature("greeting").relativeTo(getClass());
  }
}
