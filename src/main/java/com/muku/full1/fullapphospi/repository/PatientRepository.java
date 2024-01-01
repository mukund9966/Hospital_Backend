package com.muku.full1.fullapphospi.repository;

import com.muku.full1.fullapphospi.entity.Address;
import com.muku.full1.fullapphospi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {


}
