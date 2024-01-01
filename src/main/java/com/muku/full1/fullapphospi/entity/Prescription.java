package com.muku.full1.fullapphospi.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ndc;
    private String rxNumber;
    private String drugName;
    private String strength;
    private String strengthUnits;
    private String supply;
    private String refills;

}