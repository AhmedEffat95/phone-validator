package com.example.demo.service;

import com.example.demo.model.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberMapperTest {
  @Mock
  Validator mockValidator;
  @InjectMocks
  NumberMapper numberMapper;

  @Test
  void itShouldCategorizeEntities() {
    long id = 1;
    String name = "Ahmed";
    String ugandaPhone = "(256) 714660221";

    when(mockValidator.validateNumber(eq(CountryNumberType.UGANDA), eq(ugandaPhone))).thenReturn(true);

    PhoneNumber phoneNumber = new PhoneNumber(id, name, ugandaPhone);
    List<CategorizedPhoneNumber> categorizedPhoneNumbers =
        numberMapper.rawNumbersToCategorizedNumbers(Collections.singletonList(phoneNumber));
    CategorizedPhoneNumber categorizedPhoneNumber = categorizedPhoneNumbers.get(0);

    assertEquals(CountryNumberType.UGANDA, categorizedPhoneNumber.getCountryNumberType());
    assertTrue(categorizedPhoneNumber.isValid());
  }

  @Test
  void itShouldSkipBadDBDataWhenCategorizing() {
    long idValid = 1;
    String nameValid = "Ahmed";
    String ugandaPhoneValid = "(256) 714660221";

    long idInvalid = 2;
    String nameInvalid = "Ali";
    String ugandaPhoneInvalid = "(1) 0000000000";

    List<PhoneNumber> phoneNumberList = new ArrayList<>();
    phoneNumberList.add(new PhoneNumber(idValid, nameValid, ugandaPhoneValid));
    phoneNumberList.add(new PhoneNumber(idInvalid, nameInvalid, ugandaPhoneInvalid));

    List<CategorizedPhoneNumber> categorizedPhoneNumbers =
        numberMapper.rawNumbersToCategorizedNumbers(phoneNumberList);

    int expectedCategorizedNumbersCount = 1;
    assertEquals(expectedCategorizedNumbersCount, categorizedPhoneNumbers.size());
  }

  @Test
  void itShouldThrowForIllegalDBData() {
    String invalidPhone = "0000000000";
    assertThrows(IllegalStateException.class, () -> numberMapper.rawNumberToNumberType(invalidPhone));
  }

  @Test
  void itShouldMapValidNumbertoCountry() {
    String validEthiopiaNumber = "(251) 911203317";
    CountryNumberType expectedType = CountryNumberType.ETHIOPIA;

    CountryNumberType actualType = numberMapper.rawNumberToNumberType(validEthiopiaNumber);

    assertEquals(expectedType, actualType);
  }

  @Test
  void itShouldMapValidCountryNameToType() {
    String validCountryName = "Mozambique";
    Optional<CountryNumberType> type = numberMapper.countryNameToCountryType(validCountryName);

    assertTrue(type.isPresent());
    assertEquals(CountryNumberType.MOZAMBIQUE, type.get());
  }

  @Test
  void itShouldIgnoreCaseCountryNameToType() {
    String validCountryName = "CaMerooN";
    Optional<CountryNumberType> type = numberMapper.countryNameToCountryType(validCountryName);

    assertTrue(type.isPresent());
    assertEquals(CountryNumberType.CAMEROON, type.get());
  }

  @Test
  void itShouldNotReturnATypeForNonExistentCountry() {
    String nonExistentCountry =  "nonExistentCountryName";
    Optional<CountryNumberType> type = numberMapper.countryNameToCountryType(nonExistentCountry);

    assertFalse(type.isPresent());
  }
}