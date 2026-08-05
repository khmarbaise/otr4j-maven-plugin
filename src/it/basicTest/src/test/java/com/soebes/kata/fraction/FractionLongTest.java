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
 * Test for {@link FractionLong}.
 */
class FractionLongTest {

  @Nested
  class BigDecimalValue {

    @Test
    void fraction_to_bigdecimal() {
      FractionLong fraction = new FractionLong(1, 1);
      assertThat(fraction.bigDecimalValue()).isEqualByComparingTo(BigDecimal.valueOf(1));
    }
  }

  @Nested
  class DoubleValue {

    @Test
    void fraction_to_double() {
      FractionLong fraction = new FractionLong(1, 1);
      assertThat(fraction.doubleValue()).isEqualTo(1.0, Offset.offset(1E-6));
    }

    @Test
    void double_value_1_2() {
      FractionLong fraction = new FractionLong(1, 2);
      assertThat(fraction.doubleValue()).isEqualTo(0.5, Offset.offset(1E-6));
    }
  }

  @Nested
  class Negate {

    @Test
    void fraction_negate() {
      FractionLong fraction = new FractionLong(1, 1);
      assertThat(fraction.negate()).isEqualByComparingTo(new FractionLong(-1, 1));
    }

    @Test
    void fraction_negate_max() {
      FractionLong fraction = new FractionLong(Long.MAX_VALUE, 1);
      assertThat(fraction.negate()).isEqualByComparingTo(new FractionLong(-Long.MAX_VALUE, 1));
    }

    @Test
    void fraction_negate_min() {
      FractionLong fraction = new FractionLong(Long.MIN_VALUE, 1);
      assertThatExceptionOfType(ArithmeticException.class)
          .isThrownBy(fraction::negate)
          .withMessage("long overflow");
    }
  }

  @Nested
  class Signum {

    @Test
    void signum_for_z_pos_n_pos() {
      FractionLong fractionLong = new FractionLong(1, 2);
      assertThat(fractionLong.signum()).isOne();
    }

    @Test
    void signum_for_z_neg_n_pos() {
      FractionLong fractionLong = new FractionLong(-1, 2);
      assertThat(fractionLong.signum()).isNegative();
    }

    @Test
    void signum_for_z_pos_n_neg() {
      FractionLong fractionLong = new FractionLong(1, -2);
      assertThat(fractionLong.signum()).isNegative();
    }

    @Test
    void signum_for_z_neg_n_neg() {
      FractionLong fractionLong = new FractionLong(-1, -2);
      assertThat(fractionLong.signum()).isPositive();
    }
  }

  @Nested
  class CompareTo {

    @Test
    void fraction_one_identical_to_fraction_two() {
      FractionLong fractionLong_one = new FractionLong(1, 2);
      FractionLong fractionLong_two = new FractionLong(1, 2);

      assertThat(fractionLong_one).isEqualByComparingTo(fractionLong_two);
    }

    @Test
    void fraction_one_greater_than_fraction_two() {
      FractionLong fractionLong_one = new FractionLong(1, 1);
      FractionLong fractionLong_two = new FractionLong(1, 2);

      assertThat(fractionLong_one).isGreaterThan(fractionLong_two);
    }

    @Test
    void fraction_one_less_than_fraction_two() {
      FractionLong fractionLong_one = new FractionLong(1, 3);
      FractionLong fractionLong_two = new FractionLong(1, 2);

      assertThat(fractionLong_one).isLessThan(fractionLong_two);
    }

  }

  @Nested
  class Verification {

    @Test
    void hash_code_and_equals() {
      EqualsVerifier.forClass(FractionLong.class).usingGetClass().verify();
    }

    @Test
    void check_to_string() {
      FractionLong fractionLong = new FractionLong(1, 2);
      assertThat(fractionLong).hasToString("FractionLong[numerator=1, denominator=2]");
    }
  }

  @Nested
  class InvalideValues {

    @Test
    void denominator_is_not_allowed_to_be_zero() {
      assertThatIllegalArgumentException()
          .isThrownBy(() -> new FractionLong(1, 0))
          .withMessage("denominator is not allowed to be zero.");
    }

    @Test
    void compare_to_null() {
      assertThatIllegalArgumentException()
          .isThrownBy(() -> new FractionLong(1, 2).compareTo(null))
          .withMessage("compareTo is not allowed to be null.");
    }
  }

  @Nested
  class Normalizing {

    @Test
    void normalize_0_x() {
      FractionLong fractionLong = new FractionLong(0, 6);

      assertThat(fractionLong.numerator()).isZero();
      assertThat(fractionLong.denominator()).isEqualTo(1);
    }

    @Test
    void normalize_improper_fraction() {
      FractionLong improperFraction = new FractionLong(4, 6);
      assertThat(improperFraction.numerator()).isEqualTo(2);
      assertThat(improperFraction.denominator()).isEqualTo(3);
    }
  }

  @Nested
  class Multiplikation {

    @Test
    void multiply_multiplier_by_multiplicand() {
      FractionLong multiplier = new FractionLong(2, 3);
      FractionLong multiplicand = new FractionLong(4, 5);

      FractionLong produkt = multiplier.multiply(multiplicand);

      assertThat(produkt).isEqualTo(new FractionLong(8, 15));
    }
  }

  @Nested
  class Divide {

    @Test
    void devide_1_2_by_2_5() {
      FractionLong dividend = new FractionLong(1, 2);
      FractionLong divisor = new FractionLong(2, 5);

      FractionLong quotient = dividend.divide(divisor);

      assertThat(quotient).isEqualTo(new FractionLong(5, 4));
    }

    @Test
    void devide_the_same_fraction() {
      FractionLong dividend = new FractionLong(1, 2);
      FractionLong divisor = new FractionLong(1, 2);

      FractionLong quotient = dividend.divide(divisor);

      assertThat(quotient).isEqualTo(new FractionLong(1, 1));
    }
  }

  @Nested
  class Subtraction {

    @Test
    void subtract_first_minus_second() {
      FractionLong minuend = new FractionLong(2, 3);
      FractionLong subtrahend = new FractionLong(1, 5);

      FractionLong difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionLong(7, 15));
    }

    @Test
    void subtract_with_same_denominator() {
      FractionLong minuend = new FractionLong(5, 9);
      FractionLong subtrahend = new FractionLong(2, 9);

      FractionLong difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionLong(3, 9));
    }

    @Test
    void subtract_with_improper_faction_result() {
      FractionLong minuend = new FractionLong(21, 7);
      FractionLong subtrahend = new FractionLong(12, 7);

      FractionLong difference = minuend.subtract(subtrahend);

      assertThat(difference).isEqualTo(new FractionLong(9, 7));
    }
  }

  @Nested
  class Addition {

    @Test
    void chaining_addition_with_improper_fraction_result() {
      FractionLong summand_1 = new FractionLong(1, 2);
      FractionLong summand_2 = new FractionLong(1, 3);
      FractionLong summand_3 = new FractionLong(1, 4);

      FractionLong sum = summand_1.plus(summand_2).plus(summand_3);

      assertThat(sum).isEqualTo(new FractionLong(26, 24));
    }

    @Test
    void chaining_addition_with_proper_fraction_result() {
      FractionLong summand_1 = new FractionLong(1, 2);
      FractionLong summand_2 = new FractionLong(1, 3);
      FractionLong summand_3 = new FractionLong(1, 4);

      FractionLong sum = summand_1.plus(summand_2).plus(summand_3);

      assertThat(sum).isEqualTo(new FractionLong(13, 12));
    }

    @Test
    void addition_with_two_proper_fractions() {
      FractionLong summand_1 = new FractionLong(2, 3);
      FractionLong summand_2 = new FractionLong(1, 5);

      FractionLong sum = summand_1.plus(summand_2);

      assertThat(sum).isEqualTo(new FractionLong(13, 15));
    }

    @Test
    void add_1_3_plus_2_3_should_be_1_1() {
      FractionLong summand_1 = new FractionLong(1, 3);
      FractionLong summand_2 = new FractionLong(2, 3);

      FractionLong sum = summand_1.plus(summand_2);

      assertThat(sum).isEqualTo(new FractionLong(3, 3));
    }

    @Test
    void add_1_3_plus_2_3_should_be_1_1_reduced() {
      FractionLong summand_1 = new FractionLong(1, 3);
      FractionLong summand_2 = new FractionLong(2, 3);

      FractionLong sum = summand_1.plus(summand_2);

      assertThat(sum.numerator()).isEqualTo(1);
      assertThat(sum.denominator()).isEqualTo(1);
    }

  }

  @Nested
  class Pow {

    @Test
    void power_to_two() {
      FractionLong fraction = new FractionLong(2, 3);

      FractionLong produkt = fraction.pow(2);

      assertThat(produkt).isEqualTo(new FractionLong(4, 9));
    }

    @Test
    void power_to_three() {
      FractionLong fraction = new FractionLong(2, 3);

      FractionLong produkt = fraction.pow(3);

      assertThat(produkt).isEqualTo(new FractionLong(8, 27));
    }

    @Test
    void power_to_ten() {
      FractionLong fraction = new FractionLong(2, 3);

      FractionLong produkt = fraction.pow(10);

      assertThat(produkt).isEqualTo(new FractionLong(1024, 59049));
    }
  }

  @Nested
  class Limits {

    @Test
    void add_max_value() {
      FractionLong summand_1 = new FractionLong(Long.MAX_VALUE / 2, 1);
      FractionLong summand_2 = new FractionLong(Long.MAX_VALUE / 2, 1);

      FractionLong sum = summand_1.plus(summand_2);

      assertThat(sum.numerator()).isEqualTo(Long.MAX_VALUE - 1);
      assertThat(sum.denominator()).isEqualTo(1);
    }

    @Test
    void add_min_value() {
      FractionLong summand_1 = new FractionLong(Long.MIN_VALUE / 2, 1);
      FractionLong summand_2 = new FractionLong(Long.MIN_VALUE / 2, 1);

      FractionLong sum = summand_1.plus(summand_2);

      assertThat(sum.numerator()).isEqualTo(Long.MIN_VALUE);
      assertThat(sum.denominator()).isEqualTo(1);
    }

  }
}
