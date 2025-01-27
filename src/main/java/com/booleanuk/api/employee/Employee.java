package com.booleanuk.api.employee;

import com.booleanuk.api.department.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(insertable=false, updatable=false)
    private int department_id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIncludeProperties({"location", "name"})
    private Department department;


    public Employee(String firstName, String lastName) {
        this.first_name = firstName;
        this.last_name = lastName;
    }
}