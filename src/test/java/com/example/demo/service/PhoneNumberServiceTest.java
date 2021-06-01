package com.example.demo.service;

import com.example.demo.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceTest {
  @Mock
  private PhoneNumberRepository phoneNumberRepository;
  @Mock
  private NumberMapper numberMapper;
  @InjectMocks
  private PhoneNumberService phoneNumberService;

  @Test
  void itShouldReturnAllRecordsWhenCountryNameParamIsAbsent() {
    phoneNumberService.retrievePhoneNumbers(null, Optional.empty());

    verify(phoneNumberRepository, times(1)).findAll();
    verifyNoMoreInteractions(phoneNumberRepository);
  }

  @Test
  void itShouldQueryByCountryWhenCountryNameParamIsPresent() {
    String morocco = "Morocco";
    String moroccoCode = "(212)";

    when(numberMapper.countryNameToCountryType(eq(morocco))).thenReturn(Optional.of(CountryNumberType.MOROCCO));

    phoneNumberService.retrievePhoneNumbers(morocco, Optional.empty());
    verify(phoneNumberRepository, times(1)).findAllByPhoneStartingWith(moroccoCode);
    verifyNoMoreInteractions(phoneNumberRepository);
  }

  @Test
  void itShouldNotFilterByValidityIfValidityParamIsAbsent() {
    List<CategorizedPhoneNumber> categorizedPhoneNumberList = new ArrayList<>();

    categorizedPhoneNumberList.add(new CategorizedPhoneNumber(
        CountryNumberType.CAMEROON, "(237) 6622284920", true));

    categorizedPhoneNumberList.add(new CategorizedPhoneNumber(
        CountryNumberType.CAMEROON, "(237) 000000", false));

    when(numberMapper.rawNumbersToCategorizedNumbers(any())).thenReturn(categorizedPhoneNumberList);
    List<CategorizedPhoneNumber> returnedCategorizedNumbers =
        phoneNumberService.retrievePhoneNumbers(null, Optional.empty());

    assertEquals(categorizedPhoneNumberList.size(), returnedCategorizedNumbers.size());
  }

  @Test
  void itShouldFilterByValidityIfValidityParamIsPresent() {
    List<CategorizedPhoneNumber> categorizedPhoneNumberList = new ArrayList<>();

    categorizedPhoneNumberList.add(new CategorizedPhoneNumber(
        CountryNumberType.CAMEROON, "(237) 6622284920", true));

    categorizedPhoneNumberList.add(new CategorizedPhoneNumber(
        CountryNumberType.CAMEROON, "(237) 000000", false));

    when(numberMapper.rawNumbersToCategorizedNumbers(any())).thenReturn(categorizedPhoneNumberList);
    List<CategorizedPhoneNumber> returnedCategorizedNumbers =
        phoneNumberService.retrievePhoneNumbers(null, Optional.of(Boolean.TRUE));

    int expectedNumberOfFilteredPhones = 1;
    assertEquals(expectedNumberOfFilteredPhones, returnedCategorizedNumbers.size());
    assertTrue(returnedCategorizedNumbers.get(0).isValid());
  }
}