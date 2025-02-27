package com.bridgelabz.employeepayrollapp.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class EmployeePayrollDTO {
    @NotEmpty(message = "Name cannot be empty") // Ensuring the name is not empty
    @Pattern(regexp = "^[A-Z][a-zA-Z\\s]{2,}$", message = "Name must start with a capital letter and have at least 3 characters")
    private String name;

    @NotNull(message = "Salary cannot be null")
    @Min(value = 5000, message = "Minimum salary should be 5000")
    private double salary;

    @NotEmpty(message = "Gender cannot be empty")
    @Pattern(regexp = "Male|Female", message = "Gender should be either Male or Female")
    private String gender;

    @NotNull(message = "Start date cannot be null")
    @PastOrPresent(message = "Start date should be past or present")
    private LocalDate startDate;

    private String note;

    private String profilePic;

    @NotEmpty(message = "Department cannot be empty")
    private List<String> department;



//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
}