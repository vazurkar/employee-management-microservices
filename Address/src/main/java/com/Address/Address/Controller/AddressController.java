package com.Address.Address.Controller;

import com.Address.Address.DTO.AddressDTO;
import com.Address.Address.DTO.AddressRequest;
import com.Address.Address.Service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<List<AddressDTO>> addAddress(@RequestBody AddressRequest addressRequest) {

        List<AddressDTO> response = addressService.addAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }

    @PutMapping("/update")
    public ResponseEntity<List<AddressDTO>> updateAddress(@RequestBody AddressRequest addressRequest) {
        List<AddressDTO> response = addressService.updateAddress(addressRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDTO>> getAllAddress(){

        List<AddressDTO> alladdress = addressService.getall();
        return new ResponseEntity<>(alladdress, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long id){
        AddressDTO address = addressService.findById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable Long id){
        addressService.deleteaddress(id);
        return "delete successfully";
    }

    @GetMapping("/getaddressbyempid/{id}")
    public ResponseEntity<List<AddressDTO>>  getAddressByEmpId(@PathVariable Long id){
        List<AddressDTO> response = addressService.getAddressByEmpId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
