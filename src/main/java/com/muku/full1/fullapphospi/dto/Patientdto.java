package com.muku.full1.fullapphospi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class Patientdto {
    String firstName;
    String lastName;
    String gender;
    Date dob;
}
