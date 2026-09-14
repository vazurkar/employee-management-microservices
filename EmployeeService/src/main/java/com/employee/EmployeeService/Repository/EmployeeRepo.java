package com.employee.EmployeeService.Repository;

import com.employee.EmployeeService.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    @Query("""
       SELECT e
       FROM Employee e
       WHERE e.empCode = :empCode
       AND e.companyName = :companyName
       """)
    Optional<Employee> findByEmpCodeAndCompanyName(
            @Param("empCode") String empCode,
            @Param("companyName") String companyName
    );
}
