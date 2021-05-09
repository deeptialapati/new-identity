package com.identity.pojos;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Builder
public class Vehicle {

    @CsvBindByName(column = "REGISTRATION")
    private String registrationNumber;

    @CsvBindByName(column = "MAKE")
    private String make;

    @CsvBindByName(column = "MODEL")
    private String model;

    @CsvBindByName(column = "COLOR")
    private String colour;

    @CsvBindByName(column = "YEAR")
    private String year;

}
