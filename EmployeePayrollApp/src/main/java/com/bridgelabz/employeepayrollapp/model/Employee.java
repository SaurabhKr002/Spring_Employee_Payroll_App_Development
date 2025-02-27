package com.bridgelabz.employeepayrollapp.model;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
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

    @ElementCollection
    private List<String> department;

    // Constructor to initialize Employee from DTO
    public Employee(Long id, String name, double salary, String gender, String note, LocalDate startDate, List<String> department, String profilePic) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.gender = gender;
        this.note = note;
        this.startDate = startDate;
        this.department = department;
        this.profilePic = profilePic;
    }


    // Method to update Employee data from DTO
    public void updateEmployeeData(EmployeePayrollDTO employeePayrollDTO) {
        this.name = employeePayrollDTO.getName();
        this.salary = employeePayrollDTO.getSalary();
        this.gender = employeePayrollDTO.getGender();
        this.startDate = employeePayrollDTO.getStartDate();
        this.note = employeePayrollDTO.getNote();
        this.profilePic = employeePayrollDTO.getProfilePic();
        this.department = employeePayrollDTO.getDepartment();
    }
}
