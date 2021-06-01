package com.example.demo.controller;

import com.example.demo.dto.PhoneDTO;
import com.example.demo.service.PhoneNumberService;
import com.example.demo.service.CategorizedPhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PhoneNumberController {

  private final PhoneNumberService phoneNumberService;

  public PhoneNumberController(PhoneNumberService phoneNumberService) {
    this.phoneNumberService = phoneNumberService;
  }

  @GetMapping("/numbers")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<PhoneDTO> retrievePhoneNumbers(
      @RequestParam(value = "countryName", required = false) String countryName,
      @RequestParam(value = "valid", required = false) Optional<Boolean> valid) {
    List<CategorizedPhoneNumber> categorizedPhoneNumbers = phoneNumberService.retrievePhoneNumbers(countryName, valid);
    return mapCategorizedNumberstoDTO(categorizedPhoneNumbers);
  }

  private List<PhoneDTO> mapCategorizedNumberstoDTO(List<CategorizedPhoneNumber> categorizedPhoneNumbers){
    List<PhoneDTO> phoneDTOList = new ArrayList<>();
    for (CategorizedPhoneNumber categorizedPhoneNumber: categorizedPhoneNumbers) {
      phoneDTOList.add(new PhoneDTO(categorizedPhoneNumber));
    }
    return phoneDTOList;
  }
}
