package com.example.demo.service;


public enum CountryNumberType {
  CAMEROON("+237", "Cameroon", "\\(237\\)\\ ?[2368]\\d{7,8}$"),
  ETHIOPIA("+251","Ethiopia", "\\(251\\)\\ ?[1-59]\\d{8}$"),
  MOROCCO("+212", "Morocco", "\\(212\\)\\ ?[5-9]\\d{8}$"),
  MOZAMBIQUE("+258","Mozambique", "\\(258\\)\\ ?[28]\\d{7,8}$"),
  UGANDA("+256","Uganda", "\\(256\\)\\ ?\\d{9}$");

  private final String countryCode;
  // Country name in English
  private final String name;
  // Regular expression to validate a country number against
  private final String validationRegex;

  CountryNumberType(String countryCode, String name, String validationRegex){
    this.countryCode = countryCode;
    this.name = name;
    this.validationRegex = validationRegex;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public String getName() {
    return this.name;
  }

  public String getValidationRegex() {
    return this.validationRegex;
  }

  public String getCountryCodeForDBQuery() {
    // Remove the "+" at the front and add round brackets
    return String.format("(%s)", this.getCountryCode().substring(1));
  }
}
