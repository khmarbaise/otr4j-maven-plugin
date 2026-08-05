package com.soebes.kata.fraction;

public interface Operation<T> {

  T plus(T add);

  T subtract(T subtrahend);

  T multiply(T factor);

  T divide(T divisor);

  T pow(int power);

  int signum();

  T negate();

  double doubleValue();
}
