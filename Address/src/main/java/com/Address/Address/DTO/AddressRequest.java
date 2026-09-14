package com.Address.Address.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private Long empId;
    private List<AddressRequestDTO> addresses;
}
