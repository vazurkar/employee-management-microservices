package com.Address.Address.DTO;

import com.Address.Address.Enum.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private Long id;

    private Long empId;

    private String street;

    private String city;

    private String state;

    private String zip;

    private String country;

    private AddressType addressType;
}
