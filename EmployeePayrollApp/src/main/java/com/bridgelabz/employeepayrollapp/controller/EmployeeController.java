package com.bridgelabz.employeepayrollapp.controller;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.exception.EmployeeNotFoundException;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import com.bridgelabz.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j; // UC7 - Added for logging
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j // UC7 - Enables logging
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

     @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        // UC7 - Logging added
        log.info("Fetching all employees...");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        // UC7 - Logging added
        log.debug("Fetching employee with ID: {}", id);

        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee addEmployee(@Valid @RequestBody EmployeePayrollDTO employeeDTO) {
        log.info("Adding new employee: {}", employeeDTO);
        return employeeService.createEmployee(employeeDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        // UC7 - Logging added
        log.warn("Deleting employee with ID: {}", id);

        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    /* @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmployeePayrollDTO employeeDTO) {
        return new Employee(employeeDTO);
    } */ // Commented to avoid conflict with new method

    @PostMapping("/createNew")
    public Employee createNewEmployee(@Valid @RequestBody EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Creating new employee from DTO: {}", employeeDTO);
        return employeeService.createEmployee(employeeDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Updating employee with ID: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    /* UC5 Methods - Commented as per requirements */

    @PostMapping("/employee-memory")
    public Employee createEmployeeInMemory(@RequestBody EmployeePayrollDTO employeeDTO) {
        // UC7 - Logging added
        log.info("Creating employee in memory: {}", employeeDTO);
        return employeeService.createEmployeeInMemory(employeeDTO);
    }

    @GetMapping("/employee-memory")
    public List<Employee> getAllEmployeesFromMemory() {
        // UC7 - Logging added
        log.info("Fetching all employees from memory...");
        return employeeService.getAllEmployeesFromMemory();
    }

    @GetMapping("/employee-memory/{id}")
    public ResponseEntity<Employee> getEmployeeByIdFromMemory(@PathVariable Long id) {
        // UC7 - Logging added
        log.debug("Fetching employee from memory with ID: {}", id);

        return employeeService.getEmployeeByIdFromMemory(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    public Employee getEmployeeByIdex(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found!"));
    }

    public Employee addEmployeeex(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployeeex(Long id, EmployeePayrollDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found!"));
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployeeex(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found!"));
        employeeRepository.delete(employee);
    }
}
