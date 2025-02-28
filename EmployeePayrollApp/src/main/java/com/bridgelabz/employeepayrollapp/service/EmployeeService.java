package com.bridgelabz.employeepayrollapp.service;

import com.bridgelabz.employeepayrollapp.dto.EmployeePayrollDTO;
import com.bridgelabz.employeepayrollapp.model.Employee;
import com.bridgelabz.employeepayrollapp.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        log.info("Retrieving all employees from the database.");
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        log.debug("Searching for employee with ID: {}", id);
        return employeeRepository.findById(id);
    }

    public Employee addEmployee(Employee employee) {
        log.info("Saving employee: {}", employee);
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        log.warn("Deleting employee with ID: {}", id);
        employeeRepository.deleteById(id);
    }

    public Employee createEmployee(EmployeePayrollDTO employeeDTO) {
        log.info("Creating employee from DTO: {}", employeeDTO);

        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setSalary(employeeDTO.getSalary());
        employee.setGender(employeeDTO.getGender());
        employee.setNote(employeeDTO.getNote());
        if (employeeDTO.getStartDate() != null) {
            employee.setStartDate(employeeDTO.getStartDate());
        }
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setProfilePic(employeeDTO.getProfilePic());

        log.debug("Employee object before saving: {}", employee);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeePayrollDTO employeeDTO) {
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

    // New UC17 Method - Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        log.info("Fetching employees from department: {}", department);
        return employeeRepository.findEmployeesByDepartment(department);
    }

    private final List<Employee> employeeList = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Employee createEmployeeInMemory(EmployeePayrollDTO employeeDTO) {
        log.info("Creating employee in memory: {}", employeeDTO);
        Employee employee = new Employee(idCounter.getAndIncrement(), employeeDTO.getName(), employeeDTO.getSalary(),
                employeeDTO.getGender(), employeeDTO.getNote(), employeeDTO.getStartDate(),
                employeeDTO.getDepartment(), employeeDTO.getProfilePic());
        employeeList.add(employee);
        return employee;
    }

    public List<Employee> getAllEmployeesFromMemory() {
        log.info("Fetching all employees from memory...");
        return new ArrayList<>(employeeList);
    }

    public Optional<Employee> getEmployeeByIdFromMemory(Long id) {
        log.debug("Searching for employee in memory with ID: {}", id);
        return employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
    }
}
