package com.example.demo.service;

import com.example.demo.model.PhoneNumber;
import com.example.demo.repository.PhoneNumberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneNumberService {
  private final NumberMapper mapper;
  private final PhoneNumberRepository phoneNumberRepository;

  public PhoneNumberService(
      PhoneNumberRepository phoneNumberRepository, NumberMapper mapper) {
    this.phoneNumberRepository = phoneNumberRepository;
    this.mapper = mapper;
  }

  public List<CategorizedPhoneNumber> retrievePhoneNumbers(String countryName, Optional<Boolean> valid) {
    Optional<CountryNumberType> countryNumberType = mapper.countryNameToCountryType(countryName);
    List<PhoneNumber> phoneNumberList;

    if (countryNumberType.isPresent()) {
      String countryCodeForQuery = countryNumberType.get().getCountryCodeForDBQuery();
      phoneNumberList = phoneNumberRepository.findAllByPhoneStartingWith(countryCodeForQuery);
    }
    else {
      phoneNumberList = phoneNumberRepository.findAll();
    }

    List<CategorizedPhoneNumber> categorizedPhoneNumbers = mapper.rawNumbersToCategorizedNumbers(phoneNumberList);
    if (valid.isPresent()) {
      boolean validityFilter = valid.get();
      return categorizedPhoneNumbers.stream().filter(number -> validityFilter==number.isValid()).collect(Collectors.toList());
    }
    return categorizedPhoneNumbers;
  }
}
