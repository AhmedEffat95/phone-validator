package com.example.demo.controller;

import com.example.demo.dto.PhoneDTO;
import com.example.demo.service.CategorizedPhoneNumber;
import com.example.demo.service.CountryNumberType;
import com.example.demo.service.PhoneNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class PhoneNumberControllerTest {
  @Mock
  private PhoneNumberService phoneNumberService;
  @InjectMocks
  private PhoneNumberController phoneNumberController;

  private List<CategorizedPhoneNumber> categorizedPhoneNumbers = new ArrayList<>();
  private List<PhoneDTO> phoneDTOS;

  @BeforeEach
  void setup(){
    categorizedPhoneNumbers.add(
        new CategorizedPhoneNumber(CountryNumberType.MOZAMBIQUE, "(258) 6770A6616", false));
    categorizedPhoneNumbers.add(
        new CategorizedPhoneNumber(CountryNumberType.CAMEROON, "(237) 12343323", true));

    when(phoneNumberService.retrievePhoneNumbers(any(), any())).thenReturn(categorizedPhoneNumbers);
    phoneDTOS = phoneNumberController.retrievePhoneNumbers(null, java.util.Optional.empty());
  }

  @Test
  void itShouldMapStatesCorrectly() {
    String valid = "valid";
    String invalid = "invalid";

    assertEquals(invalid, phoneDTOS.get(0).getValid());
    assertEquals(valid, phoneDTOS.get(1).getValid());
  }

  @Test
  void itShouldMapCountryCodeCorrectlyInDto() {
    assertEquals(CountryNumberType.MOZAMBIQUE.getCountryCode(), phoneDTOS.get(0).getCountryCode());
    assertEquals(CountryNumberType.CAMEROON.getCountryCode(), phoneDTOS.get(1).getCountryCode());
  }

  @Test
  void itShouldMapAllRecordsReturnedByServiceLayer() {
    int expectedNumberOfReturnedRecords = 2;
    assertEquals(expectedNumberOfReturnedRecords, phoneDTOS.size());
  }

}