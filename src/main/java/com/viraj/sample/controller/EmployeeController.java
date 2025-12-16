package com.viraj.sample.controller;

import com.viraj.sample.entity.Employee;
import com.viraj.sample.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/hello")
    public String getMessage() {
        return "Hello from Employee CRUD Application!";
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getone/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
