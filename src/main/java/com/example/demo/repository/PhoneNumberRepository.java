package com.example.demo.repository;

import com.example.demo.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
  List<PhoneNumber> findAllByPhoneStartingWith(String countryCode);
}
