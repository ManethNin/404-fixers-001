package com.viraj.sample.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@javax.persistence.Entity
@Table(name = "employees")
public class Employee {

    @Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private long employeeId;
    
    private String employeeName;
    private String employeeDescription;

    public Employee() {
    }

    public Employee(String employeeName, String employeeDescription) {
        this.employeeName = employeeName;
        this.employeeDescription = employeeDescription;
    }
}
