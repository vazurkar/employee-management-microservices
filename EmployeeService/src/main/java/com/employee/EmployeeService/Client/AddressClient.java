package com.employee.EmployeeService.Client;

import com.employee.EmployeeService.DTO.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ADDRESS")
public interface AddressClient {

    @GetMapping("/address/getaddressbyempid/{id}")
    List<AddressDTO> getAddressByEmpId(@PathVariable Long id);
}
