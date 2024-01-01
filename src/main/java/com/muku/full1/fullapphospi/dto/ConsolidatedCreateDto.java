package com.muku.full1.fullapphospi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// ConsolidatedCreateDto.java
public class ConsolidatedCreateDto {
    private Patientdto patientDto;
    private Insurancedto insuranceDto;
    private Addressdto addressDto;
    private Clinicaldto clinicalDto;
    private Prescriptiondto prescriptionDto;
    private Prescriberdto prescriberDto;

    // getters and setters
}
