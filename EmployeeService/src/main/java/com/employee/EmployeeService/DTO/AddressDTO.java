package com.employee.EmployeeService.DTO;


import com.employee.EmployeeService.Enum.AddressType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    private Long id;

    private Long empId;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
