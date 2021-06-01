package com.example.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
  private final Validator validator = new Validator();

  @Test
  void itShouldReturnFalseForEmptyPhone() {
    String phone = "";
    boolean result = validator.validateNumber(CountryNumberType.MOROCCO, phone);
    assertFalse(result);
  }

  @Test
  void itShouldReturnFalseForValidNumberWithoutCode() {
    String validMoroccoNumberWithoutCode = "698054317";
    boolean result = validator.validateNumber(CountryNumberType.MOROCCO, validMoroccoNumberWithoutCode);
    assertFalse(result);
  }

  @Test
  void itShouldReturnFalseForCountryCodeOnly() {
    String moroccoCountryCode = "(212)";
    boolean result = validator.validateNumber(CountryNumberType.MOROCCO, moroccoCountryCode);
    assertFalse(result);
  }

  @Test
  void itShouldReturnFalseForValidNumberInDifferentCountry() {
    String validMoroccoPhone = "(212) 633963130";
    boolean result = validator.validateNumber(CountryNumberType.UGANDA, validMoroccoPhone);
    assertFalse(result);
  }

  @Test
  void itShouldReturnTrueForValidNumber() {
    String validMoroccoPhone = "(212) 698054317";
    boolean result = validator.validateNumber(CountryNumberType.MOROCCO, validMoroccoPhone);
    assertTrue(result);
  }
}