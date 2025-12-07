package com.viraj.sample.controller;

import com.viraj.sample.entity.Employee;
import com.viraj.sample.service.EmployeeService;
import com.viraj.sample.service.VersionCompatibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/employee/")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VersionCompatibilityService versionCompatibilityService;

    @GetMapping(path = "/hello")
    public String getMessage() {
        return "Hello boot";
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
        try {
            // Using version-specific service that might break
            versionCompatibilityService.validateData(employee.getEmployeeName());
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
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // New endpoint demonstrating version-specific collection processing
    @GetMapping("/process")
    public ResponseEntity<Map<String, Object>> processEmployeeData() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            // Extract employee names to match the service expectation
            List<String> employeeNames = employees.stream()
                    .map(Employee::getEmployeeName)
                    .collect(Collectors.toList());
            Map<String, Integer> result = versionCompatibilityService.processCollections(employeeNames);
            // Convert Map<String, Integer> to Map<String, Object>
            Map<String, Object> responseMap = new HashMap<>(result);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getone/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        try {
            Employee employee = employeeService.getEmployee(employeeId);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
