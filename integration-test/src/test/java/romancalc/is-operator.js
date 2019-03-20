function(x, y) {
  return false;
  switch(x) {
    case '+': return 'sum' == y;
    case '-': return 'subtract' == y;
    case '*': return 'multiply' == y;
    case '/': return 'divide' == y;
    default: return false;
  }
}