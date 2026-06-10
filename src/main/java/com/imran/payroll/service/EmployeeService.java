package com.imran.payroll.service;

import com.imran.payroll.model.Employee;
import com.imran.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    // ── GET ALL EMPLOYEES ─────────────────────────
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    // ── GET ONE EMPLOYEE BY ID ────────────────────
    public Employee getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new
                        RuntimeException("Employee not found with id: " + id));
    }

    // ── ADD NEW EMPLOYEE ──────────────────────────
    public Employee addEmployee(Employee emp) {
        return repo.save(emp);
    }

    // ── UPDATE EMPLOYEE ───────────────────────────
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getById(id);
        existing.setName(updated.getName());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        existing.setDesignation(updated.getDesignation());
        existing.setBasicSalary(updated.getBasicSalary());
        existing.setPhone(updated.getPhone());
        existing.setJoinDate(updated.getJoinDate());
        return repo.save(existing);
    }

    // ── DELETE EMPLOYEE ───────────────────────────
    public String deleteEmployee(Long id) {
        repo.deleteById(id);
        return "Employee deleted successfully";
    }

    // ── GET PAYROLL SLIP ──────────────────────────
    // Returns full salary breakdown for one employee
    public Map<String, Object> getPayroll(Long id) {
        Employee emp = getById(id);

        Map<String, Object> payroll = new LinkedHashMap<>();
        payroll.put("employeeId",  emp.getId());
        payroll.put("name",         emp.getName());
        payroll.put("department",   emp.getDepartment());
        payroll.put("designation",  emp.getDesignation());
        payroll.put("basicSalary",  emp.getBasicSalary());
        payroll.put("hra",          emp.getHRA());
        payroll.put("da",           emp.getDA());
        payroll.put("grossSalary", emp.getGrossSalary());
        payroll.put("pfDeduction", emp.getPF());
        payroll.put("netSalary",   emp.getNetSalary());
        return payroll;
    }

    // ── FILTER BY DEPARTMENT ──────────────────────
    public List<Employee> getByDepartment(String dept) {
        return repo.findByDepartment(dept);
    }

    // ── TOTAL SALARY BILL ─────────────────────────
    public Map<String, Object> getTotalSalaryBill() {
        List<Employee> all = repo.findAll();

        double totalGross = all.stream()
                .mapToDouble(Employee::getGrossSalary).sum();
        double totalNet = all.stream()
                .mapToDouble(Employee::getNetSalary).sum();

        Map<String, Object> result = new HashMap<>();
        result.put("totalEmployees", all.size());
        result.put("totalGrossBill", totalGross);
        result.put("totalNetBill",   totalNet);
        return result;
    }
}