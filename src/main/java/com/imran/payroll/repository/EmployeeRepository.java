package com.imran.payroll.repository;

import com.imran.payroll.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);

    // Custom: search employees by name keyword
    List<Employee> findByNameContaining(String keyword);
}