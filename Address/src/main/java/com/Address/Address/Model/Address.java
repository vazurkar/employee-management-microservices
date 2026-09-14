package com.Address.Address.Model;

import com.Address.Address.Enum.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private Long empId;
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String zip;
    @Column
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AddressType addressType;


}
