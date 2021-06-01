package com.example.demo.service;

import com.example.demo.model.PhoneNumber;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class NumberMapper {
  private final Validator validator;
  private static final String PLUS = "+";

  public NumberMapper(Validator validator) {
    this.validator = validator;
  }

  public CountryNumberType rawNumberToNumberType(String phone) throws IllegalStateException {
    String rawPhoneCode = PLUS + phone.substring(1, 4);
    Optional<CountryNumberType> countryNumberType = Arrays.stream(
        CountryNumberType.values()).filter(type -> type.getCountryCode().equals(rawPhoneCode)).findAny();

    if (!countryNumberType.isPresent()) {
      throw new IllegalStateException("Invalid DB data. Number could not be mapped to a Country Type");
    }
    return countryNumberType.get();
  }

  public List<CategorizedPhoneNumber> rawNumbersToCategorizedNumbers(List<PhoneNumber> phoneNumberList) {
    List<CategorizedPhoneNumber> categorizedPhoneNumbers = new ArrayList<>();
    for (PhoneNumber number: phoneNumberList) {
      String rawPhone = number.getPhone();
      CountryNumberType type;
      try {
         type = rawNumberToNumberType(rawPhone);
      }
      catch (IllegalStateException e) {
        continue;
      }
      boolean isValid = validator.validateNumber(type, rawPhone);
      categorizedPhoneNumbers.add(new CategorizedPhoneNumber(type, rawPhone, isValid));
    }
    return categorizedPhoneNumbers;
  }

  public Optional<CountryNumberType> countryNameToCountryType(String countryName) {
    return Arrays.stream(CountryNumberType.values()).filter(type -> type.getName().equalsIgnoreCase(countryName)).findAny();
  }
}
