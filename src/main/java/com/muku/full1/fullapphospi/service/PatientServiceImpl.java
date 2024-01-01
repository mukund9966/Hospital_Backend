package com.muku.full1.fullapphospi.service;

import com.muku.full1.fullapphospi.dto.ConsolidatedCreateDto;
import com.muku.full1.fullapphospi.dto.Prescriberdto;
import com.muku.full1.fullapphospi.entity.*;
import com.muku.full1.fullapphospi.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private InsuranceRepository insuranceRepository;
    private AddressRepository addressRepository;
    private ClinicalRepository clinicalRepository;
    private PrescriptionRepository prescriptionRepository;
    private PrescriberRepository prescriberRepository;
    private AllergyRepository allergyRepository;

    @Override
    public Patient createPatient(String firstName, String lastName, String gender, Date dob) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is required.");
        }

        Patient p1 = new Patient();
        p1.setFirstName(firstName);
        p1.setLastName(lastName);
        p1.setGender(gender);
        p1.setDob(dob);
        return patientRepository.save(p1);
    }


    @Override
    public void deletePatientByid(Long id) {
        Optional<Patient> p = patientRepository.findById(id);
        if (p.isEmpty()) {
            throw new RuntimeException("wrong id");
        }
        Patient patient = p.get();
        patientRepository.delete(patient);
    }

    @Override
    public Patient updatePatient(Long id, String firstName, String lastName, String gender) {
        Optional<Patient> optionalPatient = patientRepository.findById(id);

        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setGender(gender);


            return patientRepository.save(patient);
        } else {
            throw new RuntimeException("Patient not found with id: " + id);
        }
    }

    @Override
    public Insurance createInsurance(Long id, String rxBin) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return null;
        }
        Patient patient = patientOptional.get();
        Insurance insurance = new Insurance();
        insurance.setFirstName(patient.getFirstName());
        insurance.setLastName(patient.getLastName());
        insurance.setRxBin(rxBin);
        patient.setInsurance(insurance);
        insuranceRepository.save(insurance);
        return insurance;
    }

    @Override
    public String deleteInsuranceById(Long id) {
        Optional<Insurance> insuranceOption = insuranceRepository.findById(id);
        if (insuranceOption.isEmpty()) {
            return null;
        }
        Insurance insurance = insuranceOption.get();
        insuranceRepository.delete(insurance);
        return "deleted";
    }

    @Override
    public Address createAddress(Long id, String type, String line1, String line2, String city, String zipcode) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return null;
        }
        Patient patient = patientOptional.get();
        Address address = new Address();
        address.setType(type);
        address.setLine1(line1);
        address.setCity(city);
        address.setLine2(line2);
        address.setZipcode(zipcode);
        patient.getAddresses().add(address);
        addressRepository.save(address);
        return address;
    }

    @Override
    public String deleteAddressById(Long id) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isEmpty()) {
            return null;
        }
        Address address = addressOptional.get();
        addressRepository.delete(address);
        return "deleted";
    }

    

    @Override
    public Patient getPatientbyId(Long id) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return null;
        }
        Patient p = patientOptional.get();
        return p;
    }

    @Override
    public Clinical createClinical(Long id, Double height, Double weight) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return null;
        }
        Patient patient = patientOptional.get();
        Clinical clinical = new Clinical();
        clinical.setHeight(height);
        clinical.setWeight(weight);
        patient.setClinical(clinical);
        clinicalRepository.save(clinical);
        return clinical;
    }

    @Override
    public String deleteClinicalbyId(Long id) {
        Optional<Clinical> clinicalOptional = clinicalRepository.findById(id);
        if (clinicalOptional.isEmpty()) {
            throw new RuntimeException("error");
        }
        Clinical clinical = clinicalOptional.get();
        clinicalRepository.delete(clinical);
        return "deleted";
    }



    @Override
    public Prescription createPrescription(Long id, String ndc, String rxNumber, String drugName, String strength, String strengthUnits, String supply, String refills) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            throw new RuntimeException("error");
        }
        Patient patient = patientOptional.get();
        Prescription prescription = new Prescription();
        prescription.setNdc(ndc);
        prescription.setStrength(strength);
        prescription.setRefills(refills);
        prescription.setSupply(supply);
        prescription.setStrengthUnits(strengthUnits);
        patient.getPrescriptions().add(prescription);
        prescriptionRepository.save(prescription);
        return prescription;

    }

    @Override
    public String deletePrescription(Long id) {
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(id);
        if (prescriptionOptional.isEmpty()) {
            throw new RuntimeException("error");

        }
        Prescription prescription = prescriptionOptional.get();
        prescriptionRepository.delete(prescription);
        return "deleted";
    }

    @Override
    public Prescriber createPrescriber(Long id, String npi, String firstName, String lastName) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isEmpty()) {
            return null;
        }
        Patient patient = patientOptional.get();
        Prescriber prescriber = new Prescriber();
        prescriber.setFirstName(firstName);
        prescriber.setLastName(lastName);
        prescriber.setNpi(npi);
        patient.setPrescriber(prescriber);
        prescriberRepository.save(prescriber);
        return prescriber;

    }

    @Override
    public Patient createPatientAndRelatedInfo(ConsolidatedCreateDto consolidatedCreateDto) {
        String firstName = consolidatedCreateDto.getPatientDto().getFirstName();
        String lastName = consolidatedCreateDto.getPatientDto().getLastName();
        String gender = consolidatedCreateDto.getPatientDto().getGender();

        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }


        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setGender(gender);

        Patient createdPatient = patientRepository.save(patient);

        String rxBin = consolidatedCreateDto.getInsuranceDto().getRxBin();
        createInsurance(createdPatient.getId(), rxBin);

        String type = consolidatedCreateDto.getAddressDto().getType();
        String line1 = consolidatedCreateDto.getAddressDto().getLine1();
        String line2 = consolidatedCreateDto.getAddressDto().getLine2();
        String city = consolidatedCreateDto.getAddressDto().getCity();
        String zipcode = consolidatedCreateDto.getAddressDto().getZipcode();
        createAddress(createdPatient.getId(), type, line1, line2, city, zipcode);

        Double height = consolidatedCreateDto.getClinicalDto().getHeight();
        Double weight = consolidatedCreateDto.getClinicalDto().getWeight();
        createClinical(createdPatient.getId(), height, weight);

        String ndc = consolidatedCreateDto.getPrescriptionDto().getNdc();
        String rxNumber = consolidatedCreateDto.getPrescriptionDto().getRxNumber();
        String drugName = consolidatedCreateDto.getPrescriptionDto().getDrugName();
        String strength = consolidatedCreateDto.getPrescriptionDto().getStrength();
        String strengthUnits = consolidatedCreateDto.getPrescriptionDto().getStrengthUnits();
        String refills = consolidatedCreateDto.getPrescriptionDto().getRefills();
        String supply = consolidatedCreateDto.getPrescriptionDto().getSupply();
        createPrescription(createdPatient.getId(), ndc, rxNumber, drugName, strength, strengthUnits, refills, supply);

        String npi = consolidatedCreateDto.getPrescriberDto().getNpi();
        String prescriberFirstName = consolidatedCreateDto.getPrescriberDto().getFirstName();
        String prescriberLastName = consolidatedCreateDto.getPrescriberDto().getLastName();
        createPrescriber(createdPatient.getId(), npi, prescriberFirstName, prescriberLastName);

        return createdPatient;
    }

    @Override
    public Patient getAllPatientDetailsById(Long id) {

            Optional<Patient> patientOptional = patientRepository.findById(id);

            if (patientOptional.isPresent()) {
                Patient patient = patientOptional.get();
                patient.getInsurance();  // Fetch insurance details
                patient.getAddresses();  // Fetch address details
                patient.getClinical();   // Fetch clinical details
                patient.getPrescriptions();  // Fetch prescription details
                patient.getPrescriber();  // Fetch prescriber details

                return patient;
            } else {
                throw new EntityNotFoundException("Patient not found with id: " + id);
            }


    }

    @Override
    public Allergy createAllergy(Long id, String category, String clinicalStatus, String severity) {
        Optional<Clinical> clinicalOptional = clinicalRepository.findById(id);
        if(clinicalOptional.isEmpty()){
            return null;
        }
        Clinical clinical = clinicalOptional.get();
        Allergy allergy = new Allergy();
        allergy.setCategory(category);
        allergy.setClinicalStatus(severity);
        allergy.setSeverity(severity);
        clinical.getAllergies().add(allergy);
        allergyRepository.save(allergy);
        return allergy;

    }

    @Override
    public void deleteAllergy(Long id) {
        Optional<Allergy> allergyOptional = allergyRepository.findById(id);
        if(allergyOptional.isEmpty()){
            throw new RuntimeException();

        }
        Allergy allergy = allergyOptional.get();
        allergyRepository.delete(allergy);

    }

    @Override
    public Allergy updateAllergy(Long id,String category, String clinicalStatus, String severity) {
    Optional<Allergy> allergyOptional = allergyRepository.findById(id);
    if(allergyOptional.isPresent()){
        Allergy allergy = allergyOptional.get();
        allergy.setSeverity(severity);
        allergy.setClinicalStatus(clinicalStatus);
        allergy.setCategory(category);
        allergyRepository.save(allergy);
        return allergy;
    }
    else{
        throw new RuntimeException("Not found allergy with id"+ id);
    }

    }

    @Override
    public Insurance updateInsurance(Long id, String rxBin) {
        Optional<Insurance> insuranceOptional = insuranceRepository.findById(id);
        if (insuranceOptional.isPresent()) {
            Insurance insurance = insuranceOptional.get();
            insurance.setRxBin(rxBin);
            insuranceRepository.save(insurance);
            return insurance;
        } else {
            throw new RuntimeException("Insurance not found with id: " + id);
        }
    }

    @Override
    public Address updateAddress(Long id, String type, String line1, String line2, String city, String zipcode) {
        Optional<Address> addressOptional = addressRepository.findById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            address.setType(type);
            address.setLine1(line1);
            address.setLine2(line2);
            address.setCity(city);
            address.setZipcode(zipcode);
            addressRepository.save(address);
            return address;
        } else {
            throw new RuntimeException("Address not found with id: " + id);
        }
    }

    @Override
    public Clinical updateClinical(Long id, Double height, Double weight) {
        Optional<Clinical> clinicalOptional = clinicalRepository.findById(id);
        if (clinicalOptional.isPresent()) {
            Clinical clinical = clinicalOptional.get();
            clinical.setHeight(height);
            clinical.setWeight(weight);
            clinicalRepository.save(clinical);
            return clinical;
        } else {
            throw new RuntimeException("Clinical details not found with id: " + id);
        }
    }

    @Override
    public Prescription updatePrescription(Long id, String ndc, String rxNumber, String drugName, String strength, String strengthUnits, String supply, String refills) {
        Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(id);
        if (prescriptionOptional.isPresent()) {
            Prescription prescription = prescriptionOptional.get();
            prescription.setNdc(ndc);
            prescription.setRxNumber(rxNumber);
            prescription.setDrugName(drugName);
            prescription.setStrength(strength);
            prescription.setStrengthUnits(strengthUnits);
            prescription.setSupply(supply);
            prescription.setRefills(refills);
            prescriptionRepository.save(prescription);
            return prescription;
        } else {
            throw new RuntimeException("Prescription not found with id: " + id);
        }
    }

    @Override
    public Prescriber updatePrescriber(Long id, String npi, String firstName, String lastName) {
        Optional<Prescriber> prescriberOptional = prescriberRepository.findById(id);
        if (prescriberOptional.isPresent()) {
            Prescriber prescriber = prescriberOptional.get();
            prescriber.setNpi(npi);
            prescriber.setFirstName(firstName);
            prescriber.setLastName(lastName);
            prescriberRepository.save(prescriber);
            return prescriber;
        } else {
            throw new RuntimeException("Prescriber not found with id: " + id);
        }
    }

}
