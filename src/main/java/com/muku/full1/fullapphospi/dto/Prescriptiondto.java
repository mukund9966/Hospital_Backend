package com.muku.full1.fullapphospi.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Prescriptiondto {
     String ndc;
     String rxNumber;
     String drugName;
    private String strength;
    private String strengthUnits;
    private String supply;
    private String refills;
}
