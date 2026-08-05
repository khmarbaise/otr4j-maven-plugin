package com.soebes.kata.fraction;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apiguardian.api.API;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.StringJoiner;

import static org.apiguardian.api.API.Status.EXPERIMENTAL;

/**
 * @author Karl Heinz Marbaise
 */
@API(status = EXPERIMENTAL)
public class FractionInteger implements Comparable<FractionInteger>,Operation<FractionInteger> {
  private final int numerator;
  private final int denominator;

  public FractionInteger(int numerator, int denominator) {
    if (denominator == 0) {
      throw new IllegalArgumentException("denominator is not allowed to be zero.");
    }
    int sign = Integer.signum(numerator) * Integer.signum(denominator);

    int gcd = MathUtil.calculateGcd(numerator, denominator);
    this.numerator = sign * Math.abs(numerator) / gcd;
    this.denominator = Math.abs(denominator) / gcd;
  }

  public FractionInteger plus(FractionInteger add) {
    if (this.denominator == add.denominator) {
      return new FractionInteger(add.numerator + this.numerator, this.denominator);
    } else {
      int lNumerator = add.numerator * this.denominator + this.numerator * add.denominator;
      int lDenominator = add.denominator * this.denominator;
      return new FractionInteger(lNumerator, lDenominator);
    }
  }

  public FractionInteger subtract(FractionInteger subtrahend) {
    if (this.denominator == subtrahend.denominator) {
      return new FractionInteger(this.numerator - subtrahend.numerator, this.denominator);
    } else {
      int lNumerator = this.numerator * subtrahend.denominator - this.denominator * subtrahend.numerator;
      int lDenominator = subtrahend.denominator * this.denominator;
      return new FractionInteger(lNumerator, lDenominator);
    }
  }

  public FractionInteger multiply(FractionInteger factor) {
    return new FractionInteger(this.numerator * factor.numerator, this.denominator * factor.denominator);
  }

  public FractionInteger divide(FractionInteger divisor) {
    return new FractionInteger(this.numerator * divisor.denominator, this.denominator * divisor.numerator);
  }

  public FractionInteger pow(int power) {
    int lNumerator = BigInteger.valueOf(this.numerator).pow(power).intValueExact();
    int lDenominator = BigInteger.valueOf(this.denominator).pow(power).intValueExact();
    return new FractionInteger(lNumerator, lDenominator);
  }

  public int numerator() {
    return numerator;
  }

  public int denominator() {
    return denominator;
  }

  @Override
  public int compareTo(FractionInteger compareTo) {
    if (compareTo == null) {
      throw new IllegalArgumentException("compareTo is not allowed to be null.");
    }
    return this.subtract(compareTo).signum();
  }

  /**
   * Returns the signum function of this {@code Fraction}.
   *
   * @return -1, 0, or 1 as the value of this {@code Fraction}
   * is negative, zero, or positive.
   */
  public int signum() {
    return Integer.signum(numerator);
  }

  public FractionInteger negate() {
    return new FractionInteger(Math.negateExact(this.numerator), this.denominator);
  }

  public double doubleValue() {
    return (double) this.numerator / (double) this.denominator;
  }

  public BigDecimal bigDecimalValue() {
    return BigDecimal.valueOf(this.numerator).divide(BigDecimal.valueOf(this.denominator));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FractionInteger fractionInteger = (FractionInteger) o;
    return numerator == fractionInteger.numerator &&
        denominator == fractionInteger.denominator;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numerator, denominator);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FractionInteger.class.getSimpleName() + "[", "]")
        .add("numerator=" + numerator)
        .add("denominator=" + denominator)
        .toString();
  }

}
