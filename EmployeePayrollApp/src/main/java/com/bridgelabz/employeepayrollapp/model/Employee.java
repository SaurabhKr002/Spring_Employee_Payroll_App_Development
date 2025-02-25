package com.bridgelabz.employeepayrollapp.model;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 10000, message = "Salary must be at least 10000")
    private double salary;

//    // Default constructor (Required for JPA)
//    public Employee() {}
//
//    // Constructor for new Employee creation (Database assigns ID automatically)
//    public Employee(String name, double salary) {
//        this.name = name;
//        this.salary = salary;
//    }

    // Constructor for DTO conversion
    public Employee(EmployeePayrollDTO employeePayrollDTO) {
        this.name = employeePayrollDTO.getName();
        this.salary = employeePayrollDTO.getSalary();
    }

//    // Constructor for testing or non-JPA use cases
//    public Employee(Long id, String name, double salary) {
//        this.id = id;
//        this.name = name;
//        this.salary = salary;
//    }

//    // Getters & Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public double getSalary() { return salary; }
//    public void setSalary(double salary) { this.salary = salary; }
}