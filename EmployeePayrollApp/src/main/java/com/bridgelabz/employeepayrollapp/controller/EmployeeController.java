package com.bridgelabz.employeepayrollapp.controller;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import com.bridgelabz.employeepayrollapp.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
    @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmployeePayrollDTO employeeDTO) {
        return new Employee(employeeDTO);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to Employee Payroll App";
    }
    @PostMapping("/createNew")
    public Employee createNewEmployee(@RequestBody EmployeePayrollDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeePayrollDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    // UC5 - Create Employee in Memory
    @PostMapping("/employee-memory")
    public Employee createEmployeeInMemory(@RequestBody EmployeePayrollDTO employeeDTO) {
        return employeeService.createEmployeeInMemory(employeeDTO);
    }

    // UC5 - Get All Employees from Memory
    @GetMapping("/employee-memory")
    public List<Employee> getAllEmployeesFromMemory() {
        return employeeService.getAllEmployeesFromMemory();
    }

    // UC5 - Get Employee by ID from Memory
    @GetMapping("/employee-memory/{id}")
    public ResponseEntity<Employee> getEmployeeByIdFromMemory(@PathVariable Long id) {
        return employeeService.getEmployeeByIdFromMemory(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}