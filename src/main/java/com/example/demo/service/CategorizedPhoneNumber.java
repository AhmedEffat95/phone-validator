package com.example.demo.service;

public class CategorizedPhoneNumber {
  private final CountryNumberType countryNumberType;
  private final String number;
  private final boolean valid;

  public CountryNumberType getCountryNumberType() {
    return countryNumberType;
  }

  public String getNumber() {
    return number;
  }

  public boolean isValid() {
    return valid;
  }

  public CategorizedPhoneNumber(CountryNumberType countryNumberType, String rawPhone, boolean valid) {
    this.countryNumberType = countryNumberType;
    this.number = extractNumberFromRawPhone(rawPhone);
    this.valid = valid;
  }

  private String extractNumberFromRawPhone(String rawPhone) {
    String[] parts = rawPhone.split(" ");
    return (parts.length==2) ? parts[1] : null;
  }
}
