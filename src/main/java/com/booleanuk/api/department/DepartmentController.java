package com.booleanuk.api.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllDepartments() {
        return this.departmentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        return new ResponseEntity<Department>(this.departmentRepository.save(department), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getOneDepartment(@PathVariable int id) {
        return new ResponseEntity<Department>(this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable int id, @RequestBody Department department) {
        Department updatedDepartment = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        updatedDepartment.setLocation(department.getLocation());
        updatedDepartment.setName(department.getName());

        return new ResponseEntity<Department>(this.departmentRepository.save(updatedDepartment),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deletedDepartment(@PathVariable int id) {
        Department deletedDepartment = this.departmentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.departmentRepository.delete(deletedDepartment);
        return ResponseEntity.ok(deletedDepartment);
    }
}