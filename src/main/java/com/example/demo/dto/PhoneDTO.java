package com.example.demo.dto;

import com.example.demo.service.CategorizedPhoneNumber;

public class PhoneDTO {
  private final String countryName;
  private final String valid;
  private final String countryCode;
  private final String number;

  public String getCountryName() {
    return countryName;
  }

  public String getValid() {
    return valid;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getNumber() {
    return number;
  }

  public PhoneDTO(CategorizedPhoneNumber categorizedPhoneNumber) {
    this.countryName = categorizedPhoneNumber.getCountryNumberType().getName();
    this.valid = categorizedPhoneNumber.isValid()? "valid" : "invalid";
    this.countryCode = categorizedPhoneNumber.getCountryNumberType().getCountryCode();
    this.number = categorizedPhoneNumber.getNumber();
  }
}
