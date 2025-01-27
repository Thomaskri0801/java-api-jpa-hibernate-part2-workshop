package com.booleanuk.api.employee;


import com.booleanuk.api.department.Department;
import com.booleanuk.api.department.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        System.out.println(employee);
        System.out.println(employee.getDepartment());
        //System.out.println(employee.getDepartment().getId());
        Department department = departmentRepository.findById(employee.getDepartment_id()).orElseThrow();
        employee.setDepartment(department);
        return new ResponseEntity<Employee>(this.employeeRepository.save(employee),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable int id) {
        return new ResponseEntity<Employee>(this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Employee updatedEmployee = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updatedEmployee.setFirst_name(employee.getFirst_name());
        updatedEmployee.setLast_name(employee.getLast_name());
        updatedEmployee.setDepartment(employee.getDepartment());

        return new ResponseEntity<Employee>(this.employeeRepository.save(updatedEmployee),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deletedEmployee(@PathVariable int id) {
        Employee deletedEmployee = this.employeeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.employeeRepository.delete(deletedEmployee);
        return ResponseEntity.ok(deletedEmployee);
    }
}