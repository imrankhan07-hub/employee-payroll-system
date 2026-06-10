
package com.imran.payroll.controller;

import com.imran.payroll.model.Employee;
import com.imran.payroll.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")  // ← CRITICAL! Allows HTML page to call Java
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // GET /api/employees
    // Frontend uses this to load employee list table
    @GetMapping
    public List<Employee> getAll() {
        return service.getAllEmployees();
    }

    // GET /api/employees/1
    // Frontend uses this to load one employee's details
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getOne(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // POST /api/employees
    // Frontend uses this when Add Employee form is submitted
    @PostMapping
    public ResponseEntity<Employee> add(
            @RequestBody Employee emp) {
        return ResponseEntity.ok(service.addEmployee(emp));
    }

    // PUT /api/employees/1
    // Frontend uses this when Edit Employee form is submitted
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(
            @PathVariable Long id,
            @RequestBody Employee emp) {
        return ResponseEntity.ok(service.updateEmployee(id, emp));
    }

    // DELETE /api/employees/1
    // Frontend uses this when Delete button is clicked
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.deleteEmployee(id));
    }

    // GET /api/employees/1/payroll
    // Frontend uses this to show salary slip popup
    @GetMapping("/{id}/payroll")
    public ResponseEntity<Map> getPayroll(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getPayroll(id));
    }

    // GET /api/employees/department/IT
    // Frontend uses this to filter employees by dept
    @GetMapping("/department/{dept}")
    public List<Employee> getByDept(
            @PathVariable String dept) {
        return service.getByDepartment(dept);
    }

    // GET /api/employees/salary-bill
    // Frontend uses this to show total salary dashboard
    @GetMapping("/salary-bill")
    public ResponseEntity<Map> getSalaryBill() {
        return ResponseEntity.ok(service.getTotalSalaryBill());
    }
}