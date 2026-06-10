package com.imran.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity                       // This class = MySQL table
@Table(name = "employees")  // Table name in MySQL
@Data                         // Auto creates getters + setters
@NoArgsConstructor            // Auto creates empty constructor
@AllArgsConstructor           // Auto creates full constructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // Auto: 1, 2, 3...

    @Column(nullable = false)
    private String name;        // Employee full name

    @Column(unique = true)
    private String email;       // Unique email

    private String department;  // IT, HR, Finance, Operations
    private String designation; // Java Developer, Manager etc
    private Double basicSalary;  // Base salary amount
    private String phone;        // Phone number
    private LocalDate joinDate;  // Date when employee joined

    // ── PAYROLL CALCULATION METHODS ──────────────

    // HRA = 40% of basic salary
    public Double getHRA() {
        return basicSalary * 0.40;
    }

    // DA = 20% of basic salary
    public Double getDA() {
        return basicSalary * 0.20;
    }

    // Gross Salary = Basic + HRA + DA
    public Double getGrossSalary() {
        return basicSalary + getHRA() + getDA();
    }

    // PF Deduction = 12% of basic
    public Double getPF() {
        return basicSalary * 0.12;
    }

    // Net Salary = Gross - PF
    public Double getNetSalary() {
        return getGrossSalary() - getPF();
    }
}
