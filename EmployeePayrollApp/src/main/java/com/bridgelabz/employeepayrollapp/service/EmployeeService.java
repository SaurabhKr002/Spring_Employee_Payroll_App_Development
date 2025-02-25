package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j; // UC7 - Added for logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j // UC7 - Enables logging
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        // UC7 - Logging added
        log.info("Retrieving all employees from the database.");
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        // UC7 - Logging added
        log.debug("Searching for employee with ID: {}", id);
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {
        // UC7 - Logging added
        log.info("Saving employee: {}", employee);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        // UC7 - Logging added
        log.warn("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
    }

    public Employee createEmployee(EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Creating employee from DTO: {}", employeeDTO);

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Updating employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new RuntimeException("Employee not found with ID: " + id);
                });

        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());

        log.debug("Updated Employee Details: {}", employee);
        return employeeRepository.save(employee);
    }

    /* UC5 - In-memory storage */
    private final List<Employee> employeeList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Employee createEmployeeInMemory(EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Creating employee in memory: {}", employeeDTO);

        Employee employee = new Employee(idCounter.getAndIncrement(), employeeDTO.getName(), employeeDTO.getSalary());
        employeeList.add(employee);
        return employee;
    }

    public List<Employee> getAllEmployeesFromMemory() {
        // UC7 - Logging added
        log.info("Fetching all employees from memory...");
        return new ArrayList<>(employeeList);
    }

    public Optional<Employee> getEmployeeByIdFromMemory(Long id) {
        // UC7 - Logging added
        log.debug("Searching for employee in memory with ID: {}", id);

        return employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
    }
}
