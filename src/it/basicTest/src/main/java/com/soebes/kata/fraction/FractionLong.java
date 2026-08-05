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
public class FractionLong implements Comparable<FractionLong>, Operation<FractionLong> {
  private final long numerator;
  private final long denominator;

  public FractionLong(long numerator, long denominator) {
    if (denominator == 0) {
      throw new IllegalArgumentException("denominator is not allowed to be zero.");
    }
    int sign = Long.signum(numerator) * Long.signum(denominator);

    long gcd = MathUtil.calculateGcd(numerator, denominator);
    this.numerator = sign * Math.abs(numerator) / gcd;
    this.denominator = Math.abs(denominator) / gcd;
  }

  // region Add
  public FractionLong plus(FractionLong add) {
    if (this.denominator == add.denominator) {
      return new FractionLong(add.numerator + this.numerator, this.denominator);
    } else {
      long lNumerator = add.numerator * this.denominator + this.numerator * add.denominator;
      long lDenominator = add.denominator * this.denominator;
      return new FractionLong(lNumerator, lDenominator);
    }
  }

  public FractionLong subtract(FractionLong subtrahend) {
    if (this.denominator == subtrahend.denominator) {
      return new FractionLong(this.numerator - subtrahend.numerator, this.denominator);
    } else {
      long lNumerator = this.numerator * subtrahend.denominator - this.denominator * subtrahend.numerator;
      long lDenominator = subtrahend.denominator * this.denominator;
      return new FractionLong(lNumerator, lDenominator);
    }
  }
  // endregion Add

  public FractionLong multiply(FractionLong factor) {
    return new FractionLong(this.numerator * factor.numerator, this.denominator * factor.denominator);
  }

  public FractionLong divide(FractionLong divisor) {
    return new FractionLong(this.numerator * divisor.denominator, this.denominator * divisor.numerator);
  }

  public FractionLong pow(int power) {
    long lNumerator = BigInteger.valueOf(this.numerator).pow(power).longValue();
    long lDenominator = BigInteger.valueOf(this.denominator).pow(power).longValueExact();
    return new FractionLong(lNumerator, lDenominator);
  }

  public long numerator() {
    return numerator;
  }

  public long denominator() {
    return denominator;
  }

  @Override
  public int compareTo(FractionLong compareTo) {
    if (compareTo == null) {
      throw new IllegalArgumentException("compareTo is not allowed to be null.");
    }
    return this.subtract(compareTo).signum();
  }

  /**
   * Returns the signum function of this {@code FractionLong}.
   *
   * @return -1, 0, or 1 as the value of this {@code FractionLong}
   * is negative, zero, or positive.
   */
  public int signum() {
    return Long.signum(numerator);
  }

  public FractionLong negate() {
    return new FractionLong(Math.negateExact(this.numerator), this.denominator);
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
    FractionLong fraction = (FractionLong) o;
    return numerator == fraction.numerator &&
        denominator == fraction.denominator;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numerator, denominator);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", FractionLong.class.getSimpleName() + "[", "]")
        .add("numerator=" + numerator)
        .add("denominator=" + denominator)
        .toString();
  }

}
