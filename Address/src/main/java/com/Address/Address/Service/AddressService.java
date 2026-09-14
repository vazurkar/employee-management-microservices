package com.Address.Address.Service;

import com.Address.Address.AddressApplication;
import com.Address.Address.Client.EmployeeClient;
import com.Address.Address.DTO.AddressDTO;
import com.Address.Address.DTO.AddressRequest;
import com.Address.Address.DTO.AddressRequestDTO;
import com.Address.Address.DTO.EmployeeDTO;
import com.Address.Address.Exception.ResourceNotFoundException;
import com.Address.Address.Model.Address;
import com.Address.Address.Repository.AddressRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressService {
    Logger log = LoggerFactory.getLogger(AddressService.class);

    private AddressRepository addressRepository;
    private ModelMapper modelMapper;
    private EmployeeClient employeeClient;

  public  AddressService(AddressRepository addressRepository , ModelMapper modelMapper,  EmployeeClient employeeClient) {
      this.addressRepository=addressRepository;
      this.modelMapper=modelMapper;
      this.employeeClient=employeeClient;
  }

  public List<AddressDTO> addAddress(AddressRequest addressRequest){
      //todo check if employee exist
      EmployeeDTO employee;
      try {
          employee = employeeClient.getEmployee(addressRequest.getEmpId());
      } catch (FeignException.NotFound e) {
          throw new ResourceNotFoundException("Employee not found with id " + addressRequest.getEmpId());
      }


     List<Address>listToSave = this.saveOrUpdate(addressRequest);
    List<Address>savedAddress =   addressRepository.saveAll(listToSave);
      return  savedAddress.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();

  }

  public List<AddressDTO> updateAddress(AddressRequest addressRequest ){
      EmployeeDTO employee;
      try {
          employee = employeeClient.getEmployee(addressRequest.getEmpId());
      } catch (FeignException.NotFound e) {
          throw new ResourceNotFoundException("Employee not found with id " + addressRequest.getEmpId());
      }

     List<Address> addressbyEmpID= addressRepository.findAllByEmpId(addressRequest.getEmpId());
     if(addressbyEmpID.isEmpty()){
         log.info("No address found with emp id "+addressRequest.getEmpId());
         log.info("creating new address with emp id "+addressRequest.getEmpId());
     }
      List<Address>listToSave = this.saveOrUpdate(addressRequest);
     List<Long> upcomingnonnull = listToSave.stream().map(Address::getId).filter(Objects::nonNull).toList();
     List<Long> existingIds = addressbyEmpID.stream().map(Address::getId).toList();

     List<Long> idsToDelete = existingIds.stream().filter(id -> !upcomingnonnull.contains(id)).toList();
     if(!idsToDelete.isEmpty()){
         addressRepository.deleteAllById(idsToDelete);
     }

     List<Address> updatedaddress = addressRepository.saveAll(listToSave);
     return  updatedaddress.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();

  }

  private List<Address> saveOrUpdate(AddressRequest addressRequest){
      List<Address> listToSave = new ArrayList<>();
      for(AddressRequestDTO addressRequestDTO:addressRequest.getAddresses()){
          Address address = new  Address();
          address.setCity(addressRequestDTO.getCity());
          address.setId(addressRequestDTO.getId() != null ? addressRequestDTO.getId() : null);
          address.setCountry(addressRequestDTO.getCountry());
          address.setStreet(addressRequestDTO.getStreet());
          address.setZip(addressRequestDTO.getZip());
          address.setState(addressRequestDTO.getState());
          address.setAddressType(addressRequestDTO.getAddressType());
          address.setEmpId(addressRequest.getEmpId());
          listToSave.add(address);

      }
      return listToSave;
  }

  public AddressDTO findById(Long id){
    Address address=  addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id is not found"));
      return modelMapper.map(address, AddressDTO.class);
  }

  public List<AddressDTO> getall(){
      List<Address>listToSave = addressRepository.findAll();
      return listToSave.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
  }

  public void deleteaddress(Long id){
      Address address=  addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id is not found"));
      addressRepository.delete(address);
  }

  public List<AddressDTO> getAddressByEmpId(Long empId){
      List<Address> addressbyEmpID= addressRepository.findAllByEmpId(empId);
      return addressbyEmpID.stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();

  }


}
