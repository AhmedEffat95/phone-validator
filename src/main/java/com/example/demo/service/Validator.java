package com.example.demo.service;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
  public boolean validateNumber(CountryNumberType type, String phone) {
    return Pattern.matches(type.getValidationRegex(), phone);
  }
}
