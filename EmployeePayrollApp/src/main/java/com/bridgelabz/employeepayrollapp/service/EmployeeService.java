package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public Employee createEmployee(EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeePayrollDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        return employeeRepository.save(employee);
    }

    // In-memory storage for UC5
    private final List<Employee> employeeList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Unique ID generator

    // New method to create an employee and store in-memory
    public Employee createEmployeeInMemory(EmployeePayrollDTO employeeDTO) {
        Employee employee = new Employee(idCounter.getAndIncrement(), employeeDTO.getName(), employeeDTO.getSalary());
        employeeList.add(employee); // Store in memory
        return employee;
    }

    // New method to fetch all employees from in-memory list
    public List<Employee> getAllEmployeesFromMemory() {
        return new ArrayList<>(employeeList);
    }

    // New method to fetch an employee by ID from in-memory list
    public Optional<Employee> getEmployeeByIdFromMemory(Long id) {
        return employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
    }

}
