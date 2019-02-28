function fn() {
  var env = karate.env; // get java system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'ide'; // a custom 'intelligent' default
  }
  var config = { // base config JSON
    appId: 'sita.aero.karate.demo.greetings',
    // appSecret: 'my.secret',
    baseAPIUrl: 'http://127.0.0.1:8088/karate/hw/'
  };
  if (env == 'docker') {
    // over-ride only those that need to be
    config.baseAPIUrl = 'http://karate-hw:8080/karate/hw';
  } else if (env == 'ide') {
    config.baseAPIUrl = 'http://127.0.0.1:8089/karate/hw';
  }
  // if (env != 'ide') {
  //   // don't waste time waiting for a connection or if servers don't respond within 5 seconds
  //   karate.configure('connectTimeout', 5000);
  //   karate.configure('readTimeout', 5000);
  // }
  return config;
}