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

import nl.jqno.equalsverifier.EqualsVerifier;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Test for {@link FractionInteger}.
 */
class FractionIntegerTest {

  @Nested
  class BigDecimalValue {

    @Test
    void fraction_to_bigdecimal() {
      FractionInteger fractionInteger = new FractionInteger(1, 1);
      assertThat(fractionInteger.bigDecimalValue()).isEqualByComparingTo(BigDecimal.valueOf(1));
    }
  }

  @Nested
  class DoubleValue {

    @Test
    void fraction_to_double() {
      FractionInteger fractionInteger = new FractionInteger(1, 1);
      assertThat(fractionInteger.doubleValue()).isEqualTo(1.0, Offset.offset(1E-6));
    }

    @Test
    void double_value_1_2() {
      FractionInteger fractionInteger = new FractionInteger(1, 2);
      assertThat(fractionInteger.doubleValue()).isEqualTo(0.5, Offset.offset(1E-6));
    }
  }

  @Nested
  class Negate {

    @Test
    void fraction_negate() {
      FractionInteger fractionInteger = new FractionInteger(1, 1);
      assertThat(fractionInteger.negate()).isEqualByComparingTo(new FractionInteger(-1, 1));
    }

    @Test
    void fraction_negate_max() {
      FractionInteger fractionInteger = new FractionInteger(Integer.MAX_VALUE, 1);
      assertThat(fractionInteger.negate()).isEqualByComparingTo(new FractionInteger(-Integer.MAX_VALUE, 1));
    }

    @Test
    void fraction_negate_min() {
      FractionInteger fractionInteger = new FractionInteger(Integer.MIN_VALUE, 1);
      assertThatExceptionOfType(ArithmeticException.class)
          .isThrownBy(fractionInteger::negate)
          .withMessage("integer overflow");
    }
  }

  @Nested
  class Signum {

    @Test
    void signum_for_z_pos_n_pos() {
      FractionInteger fractionInteger = new FractionInteger(1, 2);
      assertThat(fractionInteger.signum()).isOne();
    }

    @Test
    void signum_for_z_neg_n_pos() {
      FractionInteger fractionInteger = new FractionInteger(-1, 2);
      assertThat(fractionInteger.signum()).isNegative();
    }

    @Test
    void signum_for_z_pos_n_neg() {
      FractionInteger fractionInteger = new FractionInteger(1, -2);
      assertThat(fractionInteger.signum()).isNegative();
    }

    @Test
    void signum_for_z_neg_n_neg() {
      FractionInteger fractionInteger = new FractionInteger(-1, -2);
      assertThat(fractionInteger.signum()).isPositive();
    }
  }

  @Nested
  class CompareTo {

    @Test
    void fraction_one_identical_to_fraction_two() {
      FractionInteger fractionIntegerOne = new FractionInteger(1, 2);
      FractionInteger fractionIntegerTwo = new FractionInteger(1, 2);

      assertThat(fractionIntegerOne).isEqualByComparingTo(fractionIntegerTwo);
    }

    @Test
    void fraction_one_greater_than_fraction_two() {
      FractionInteger fractionIntegerOne = new FractionInteger(1, 1);
      FractionInteger fractionIntegerTwo = new FractionInteger(1, 2);

      assertThat(fractionIntegerOne).isGreaterThan(fractionIntegerTwo);
    }

    @Test
    void fraction_one_less_than_fraction_two() {
      FractionInteger fractionIntegerOne = new FractionInteger(1, 3);
      FractionInteger fractionIntegerTwo = new FractionInteger(1, 2);

      assertThat(fractionIntegerOne).isLessThan(fractionIntegerTwo);
    }

  }

  @Nested
  class Verification {

    @Test
    void hash_code_and_equals() {
      EqualsVerifier.forClass(FractionInteger.class).usingGetClass().verify();
    }

    @Test
    void check_to_string() {
      FractionInteger fractionInteger = new FractionInteger(1, 2);
      assertThat(fractionInteger).hasToString("FractionInteger[numerator=1, denominator=2]");
    }
  }

  @Nested
  class InvalideValues {

    @Test
    void denominator_is_not_allowed_to_be_zero() {
      assertThatIllegalArgumentException()
          .isThrownBy(() -> new FractionInteger(1, 0))
          .withMessage("denominator is not allowed to be zero.");
    }

    @Test
    void compare_to_null() {
      assertThatIllegalArgumentException()
          .isThrownBy(() -> new FractionInteger(1, 2).compareTo(null))
          .withMessage("compareTo is not allowed to be null.");
    }
  }

  @Nested
  class Normalizing {

    @Test
    void normalize_0_x() {
      FractionInteger fractionInteger = new FractionInteger(0, 6);

      assertThat(fractionInteger.numerator()).isZero();
      assertThat(fractionInteger.denominator()).isOne();
    }

    @Test
    void normalize_improper_fraction() {
      FractionInteger improperFractionInteger = new FractionInteger(4, 6);
      assertThat(improperFractionInteger.numerator()).isEqualTo(2);
      assertThat(improperFractionInteger.denominator()).isEqualTo(3);
    }
  }

  @Nested
  class Multiplikation {

    @Test
    void multiply_multiplier_by_multiplicand() {
      FractionInteger multiplier = new FractionInteger(2, 3);
      FractionInteger multiplicand = new FractionInteger(4, 5);

      FractionInteger produkt = multiplier.multiply(multiplicand);

      assertThat(produkt).isEqualTo(new FractionInteger(8, 15));
    }
  }

  @Nested
  class Divide {

    @Test
    void divide_1_2_by_2_5() {
      FractionInteger dividend = new FractionInteger(1, 2);
      FractionInteger divisor = new FractionInteger(2, 5);

      FractionInteger quotient = dividend.divide(divisor);

      assertThat(quotient).isEqualTo(new FractionInteger(5, 4));
    }

    @Test
    void divide_the_same_fraction() {
      FractionInteger dividend = new FractionInteger(1, 2);
      FractionInteger divisor = new FractionInteger(1, 2);

      FractionInteger quotient = dividend.divide(divisor);

      assertThat(quotient).isEqualTo(new FractionInteger(1, 1));
    }
  }

  @Nested
  class Subtraction {

    @Test
    void subtract_first_minus_second() {
      FractionInteger minuend = new FractionInteger(2, 3);
      FractionInteger subtrahend = new FractionInteger(1, 5);

      FractionInteger difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionInteger(7, 15));
    }

    @Test
    void subtract_with_same_denominator() {
      FractionInteger minuend = new FractionInteger(5, 9);
      FractionInteger subtrahend = new FractionInteger(2, 9);

      FractionInteger difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionInteger(3, 9));
    }

    @Test
    void subtract_with_improper_faction_result() {
      FractionInteger minuend = new FractionInteger(21, 7);
      FractionInteger subtrahend = new FractionInteger(12, 7);

      FractionInteger difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionInteger(9, 7));
    }
  }

  @Nested
  class Addition {

    @Test
    void chaining_addition_with_improper_fraction_result() {
      FractionInteger summand1 = new FractionInteger(1, 2);
      FractionInteger summand2 = new FractionInteger(1, 3);
      FractionInteger summand3 = new FractionInteger(1, 4);

      FractionInteger sum = summand1.plus(summand2).plus( summand3 );

      assertThat(sum).isEqualTo(new FractionInteger(26, 24));
    }

    @Test
    void chaining_addition_with_proper_fraction_result() {
      FractionInteger summand1 = new FractionInteger(1, 2);
      FractionInteger summand2 = new FractionInteger(1, 3);
      FractionInteger summand3 = new FractionInteger(1, 4);

      FractionInteger sum = summand1.plus( summand2 ).plus( summand3 );

      assertThat(sum).isEqualTo(new FractionInteger(13, 12));
    }

    @Test
    void addition_with_two_proper_fractions() {
      FractionInteger summand1 = new FractionInteger(2, 3);
      FractionInteger summand2 = new FractionInteger(1, 5);

      FractionInteger sum = summand1.plus(summand2);

      assertThat(sum).isEqualTo(new FractionInteger(13, 15));
    }

    @Test
    void add_1_3_plus_2_3_should_be_1_1() {
      FractionInteger summand1 = new FractionInteger(1, 3);
      FractionInteger summand2 = new FractionInteger(2, 3);

      FractionInteger sum = summand1.plus( summand2 );

      assertThat(sum).isEqualTo(new FractionInteger(3, 3));
    }

    @Test
    void add_1_3_plus_2_3_should_be_1_1_reduced() {
      FractionInteger summand1 = new FractionInteger(1, 3);
      FractionInteger summand2 = new FractionInteger(2, 3);

      FractionInteger sum = summand1.plus( summand2 );

      assertThat(sum.numerator()).isEqualTo(1);
      assertThat(sum.denominator()).isEqualTo(1);
    }

  }

  @Nested
  class Pow {

    @Test
    void power_to_two() {
      FractionInteger fractionInteger = new FractionInteger(2, 3);

      FractionInteger produkt = fractionInteger.pow(2);

      assertThat(produkt).isEqualTo(new FractionInteger(4, 9));
    }

    @Test
    void power_to_three() {
      FractionInteger fractionInteger = new FractionInteger(2, 3);

      FractionInteger produkt = fractionInteger.pow(3);

      assertThat(produkt).isEqualTo(new FractionInteger(8, 27));
    }

    @Test
    void power_to_ten() {
      FractionInteger fractionInteger = new FractionInteger(2, 3);

      FractionInteger produkt = fractionInteger.pow(10);

      assertThat(produkt).isEqualTo(new FractionInteger(1024, 59049));
    }
  }

  @Nested
  class Limits {

    @Test
    void add_max_value() {
      FractionInteger summand1 = new FractionInteger(Integer.MAX_VALUE / 2, 1);
      FractionInteger summand2 = new FractionInteger(Integer.MAX_VALUE / 2, 1);

      FractionInteger sum = summand1.plus( summand2 );

      assertThat(sum.numerator()).isEqualTo(Integer.MAX_VALUE - 1);
      assertThat(sum.denominator()).isEqualTo(1);
    }

    @Test
    void add_min_value() {
      FractionInteger summand1 = new FractionInteger(Integer.MIN_VALUE / 2, 1);
      FractionInteger summand2 = new FractionInteger(Integer.MIN_VALUE / 2, 1);

      FractionInteger sum = summand1.plus( summand2 );

      assertThat(sum.numerator()).isEqualTo(Integer.MIN_VALUE);
      assertThat(sum.denominator()).isEqualTo(1);
    }

  }
}
