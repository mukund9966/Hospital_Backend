package com.muku.full1.fullapphospi.controller;

import com.muku.full1.fullapphospi.dto.*;
import com.muku.full1.fullapphospi.entity.*;
import com.muku.full1.fullapphospi.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class PatientController {
    private PatientService patientService;

    @PostMapping("/patient/create")
    public ResponseEntity<Patient> createPatient(@RequestBody Patientdto patientdto) {
        try {
            if (patientdto.getFirstName() == null || patientdto.getFirstName().isEmpty()) {
                throw new IllegalArgumentException("First name is required.");
            }

            Patient createdPatient = patientService.createPatient(patientdto.getFirstName(), patientdto.getLastName(), patientdto.getGender(), patientdto.getDob());
            return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/patient/delete/{id}")
    public void deletePatientById(@PathVariable("id") Long id){
        patientService.deletePatientByid(id);
    }

    @PutMapping("/patient/update/{id}")
    public ResponseEntity<Patient> updatePatientDetails(
            @PathVariable Long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String gender) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, firstName, lastName, gender);
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/insurance/create/{id}")
    public Insurance createInsurance(@PathVariable("id") Long id , @RequestBody Insurancedto insurancedto){
        return patientService.createInsurance(id, insurancedto.getRxBin());
    }

    @DeleteMapping("insurance/delete/{id}")
    public void deleteOInsurancebyId(@PathVariable("id") Long id){
        patientService.deleteInsuranceById(id);
    }

    @PostMapping("/address/create/{id}")
    public Addressdto createAddress(@PathVariable("id") Long id, @RequestBody Addressdto addressdto){
        Address address = patientService.createAddress(id, addressdto.getType(), addressdto.getLine1(), addressdto.getLine2(), addressdto.getCity(), addressdto.getZipcode());
        return addressdto;
    }

    @DeleteMapping("/address/delete/{id}")

    public void deleteAddressById(@PathVariable("id") Long id){
        patientService.deleteAddressById(id);
    }


    @GetMapping("/patient/{id}")
    public Patient getPatientbyId(@PathVariable("id") Long id){
        return patientService.getPatientbyId(id);
    }

    @GetMapping("/getAllPatientDetail/{id}")

    public ResponseEntity<Patient> getPatientDetailsById(@PathVariable Long id) {
        Patient patient = patientService.getAllPatientDetailsById(id);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PostMapping("/clinical/create/{id}")
    public Clinicaldto createClinical(@PathVariable("id") Long id, @RequestBody Clinicaldto clinicaldto){
        Clinical clinical = patientService.createClinical(id, clinicaldto.getHeight(), clinicaldto.getWeight());
        return clinicaldto;
    }

    @DeleteMapping("/clinical/delete/{id}")
    public void deleteClinicalbyId(@PathVariable("id") Long id){
        patientService.deleteClinicalbyId(id);
    }

    @PostMapping("/prescription/create/{id}")
    public Prescriptiondto createPrescription(@PathVariable("id") Long id, @RequestBody Prescriptiondto prescriptiondto){
        Prescription prescription = patientService.createPrescription(id,prescriptiondto.getNdc(), prescriptiondto.getRxNumber(),prescriptiondto.getDrugName(),prescriptiondto.getStrength(), prescriptiondto.getStrengthUnits(), prescriptiondto.getRefills(), prescriptiondto.getSupply());
        return prescriptiondto;

    }

    @DeleteMapping("/prescription/delete/{id}")
    public void deletePrescription(@PathVariable("id") Long id){
        patientService.deletePrescription(id);
    }

@PostMapping("/prescriber/create/{id}")
    public Prescriber createPrescriber(@PathVariable("id") Long id, @RequestBody Prescriberdto prescriberdto){
Prescriber prescriber = patientService.createPrescriber(id, prescriberdto.getNpi(), prescriberdto.getFirstName(), prescriberdto.getLastName());
return prescriber;
    }
    @PostMapping("/patient/create-consolidated")
    public ResponseEntity<Patient> createConsolidatedPatient(@RequestBody ConsolidatedCreateDto consolidatedCreateDto) {
        try {
            if (consolidatedCreateDto.getPatientDto().getFirstName() == null || consolidatedCreateDto.getPatientDto().getFirstName().isEmpty()) {
                throw new IllegalArgumentException("First name is required");
            }

            Patient createdPatient = patientService.createPatientAndRelatedInfo(consolidatedCreateDto);
            return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

        @GetMapping("/allergy/create/{id}")
                public Allergy createAllergy(@PathVariable("id") Long id, @RequestBody Allergydto allergydto){
            Allergy allergy = patientService.createAllergy(id, allergydto.getCategory(), allergydto.getClinicalStatus(), allergydto.getSeverity());
            return allergy;

    }
    @PutMapping("/insurance/update/{id}")
    public ResponseEntity<Insurance> updateInsurance(@PathVariable Long id, @RequestBody Insurancedto insurancedto) {
        try {
            Insurance updatedInsurance = patientService.updateInsurance(id, insurancedto.getRxBin());
            return new ResponseEntity<>(updatedInsurance, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/address/update/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Addressdto addressdto) {
        try {
            Address updatedAddress = patientService.updateAddress(id, addressdto.getType(), addressdto.getLine1(), addressdto.getLine2(), addressdto.getCity(), addressdto.getZipcode());
            return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/clinical/update/{id}")
    public ResponseEntity<Clinical> updateClinical(@PathVariable Long id, @RequestBody Clinicaldto clinicaldto) {
        try {
            Clinical updatedClinical = patientService.updateClinical(id, clinicaldto.getHeight(), clinicaldto.getWeight());
            return new ResponseEntity<>(updatedClinical, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/prescription/update/{id}")
    public ResponseEntity<Prescription> updatePrescription(@PathVariable Long id, @RequestBody Prescriptiondto prescriptiondto) {
        try {
            Prescription updatedPrescription = patientService.updatePrescription(id, prescriptiondto.getNdc(), prescriptiondto.getRxNumber(), prescriptiondto.getDrugName(), prescriptiondto.getStrength(), prescriptiondto.getStrengthUnits(), prescriptiondto.getSupply(), prescriptiondto.getRefills());
            return new ResponseEntity<>(updatedPrescription, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/prescriber/update/{id}")
    public ResponseEntity<Prescriber> updatePrescriber(@PathVariable Long id, @RequestBody Prescriberdto prescriberdto) {
        try {
            Prescriber updatedPrescriber = patientService.updatePrescriber(id, prescriberdto.getNpi(), prescriberdto.getFirstName(), prescriberdto.getLastName());
            return new ResponseEntity<>(updatedPrescriber, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
