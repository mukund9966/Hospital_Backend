package com.muku.full1.fullapphospi.service;

import com.muku.full1.fullapphospi.dto.ConsolidatedCreateDto;
import com.muku.full1.fullapphospi.entity.*;

import java.util.Date;

public interface PatientService {
    Patient createPatient(String firstName, String lastName, String Gender, Date dob);
    void deletePatientByid(Long id);
    Patient updatePatient(Long id, String firstName, String lastName, String gender);
    Insurance createInsurance(Long id, String rxBin);
    String deleteInsuranceById(Long id);
    Address createAddress(Long id, String type, String line1, String line2, String city, String zipcode);
    String deleteAddressById(Long id);

    Patient getPatientbyId(Long id);

    Clinical createClinical(Long id, Double height, Double weight);

    String deleteClinicalbyId(Long id);

    Prescription createPrescription(Long id,  String ndc,
     String rxNumber,
     String drugName,
     String strength,
     String strengthUnits,
     String supply,
     String refills);


String deletePrescription(Long id);

Prescriber createPrescriber(Long id, String npi,
     String firstName,
     String lastName);

    Patient createPatientAndRelatedInfo(ConsolidatedCreateDto consolidatedCreateDto);
    Patient getAllPatientDetailsById(Long id);

Allergy createAllergy( Long id,String category,
     String clinicalStatus,
     String severity);
void deleteAllergy(Long id);

Allergy updateAllergy(Long id,String category,
                      String clinicalStatus,
                      String severity);


    Insurance updateInsurance(Long id, String rxBin);
    Address updateAddress(Long id, String type, String line1, String line2, String city, String zipcode);
    Clinical updateClinical(Long id, Double height, Double weight);
    Prescription updatePrescription(Long id, String ndc, String rxNumber, String drugName, String strength, String strengthUnits, String supply, String refills);
    Prescriber updatePrescriber(Long id, String npi, String firstName, String lastName);
        };


