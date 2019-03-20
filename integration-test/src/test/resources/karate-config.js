function fn() {
  var env = karate.env;
  if (!env) {
    env = 'ide';
  }
  karate.log('karate.env system property was:', env);
  var config = { // base config JSON
    appId: 'sita.aero.karate.demo.greetings',
    // appSecret: 'my.secret',
    baseAPIUrl: 'http://127.0.0.1:8888/roman'
  };
  if (env == 'docker') {
    // over-ride only those that need to be
    config.baseAPIUrl = 'http://romancalc-rs:8080/roman';
  } else if (env == 'ide') {
    config.baseAPIUrl = 'http://127.0.0.1:8888/roman';
  }
  return config;
}