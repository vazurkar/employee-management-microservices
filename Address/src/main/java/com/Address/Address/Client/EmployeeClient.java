package com.Address.Address.Client;

import com.Address.Address.DTO.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="employeeClient", url="${employee.service.url}")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    EmployeeDTO getEmployee(@PathVariable Long id);
}
