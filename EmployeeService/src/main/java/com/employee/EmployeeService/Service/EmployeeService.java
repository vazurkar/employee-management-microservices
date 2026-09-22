package com.employee.EmployeeService.Service;

import com.employee.EmployeeService.Client.AddressClient;
import com.employee.EmployeeService.DTO.AddressDTO;
import com.employee.EmployeeService.DTO.EmployeeDTO;
import com.employee.EmployeeService.Exception.BadRequestException;
import com.employee.EmployeeService.Exception.ResourceNotFoundException;
import com.employee.EmployeeService.Model.Employee;
import com.employee.EmployeeService.Repository.EmployeeRepo;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;

    @Autowired
    private RedisService redisService;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper, AddressClient addressClient) {
        this.employeeRepo = employeeRepo;
        this.addressClient = addressClient;
        this.modelMapper = modelMapper;
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() != null) {
            throw new RuntimeException();
        }
        Employee emp = modelMapper.map(employeeDTO, Employee.class);
        Employee newEmp = employeeRepo.save(emp);
        return modelMapper.map(newEmp, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (id == null || employeeDTO.getId() == null || !Objects.equals(employeeDTO.getId(), id)) {
            throw new BadRequestException("Please provide correct employee id");
        }
        Employee emp = modelMapper.map(employeeDTO, Employee.class);
        Employee newEmp = employeeRepo.save(emp);
        return modelMapper.map(newEmp, EmployeeDTO.class);
    }

    public void deleteEmployee(Long id) {
        Employee emp = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found" + id));
        employeeRepo.delete(emp);
    }

    public EmployeeDTO getEmployee(Long id) {

        EmployeeDTO employeeDTO = redisService.getEmployee(id, EmployeeDTO.class);
        if (employeeDTO != null) {
            log.info("REsult returend from cache");
            return employeeDTO;
        } else {
            Employee emp = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found" + id));

            List<AddressDTO> addresses = new ArrayList<>();
            try {
                addresses = addressClient.getAddressByEmpId(id);
            } catch (FeignException.NotFound e) {
                log.error("Address not found with id " + id);
            }

            EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
            dto.setAddresses(addresses);
            redisService.setEmployee(id, dto, 300L);
            log.info("REsult returend from database");
            return dto;
        }
    }

    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        List<EmployeeDTO> empployeeDtoList = employees.stream().map(emp -> modelMapper.map(emp, EmployeeDTO.class)).toList();
        List<EmployeeDTO> response = new ArrayList<>();
        for (EmployeeDTO employeeDTO : empployeeDtoList) {
            List<AddressDTO> addresses = new ArrayList<>();
            try {
                addresses = addressClient.getAddressByEmpId(employeeDTO.getId());
            } catch (FeignException.NotFound e) {
                log.error("Address not found with id " + employeeDTO.getId());
            }
            employeeDTO.setAddresses(addresses);
        }
        response.addAll(empployeeDtoList);
        return response;
    }

    public EmployeeDTO getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
        Employee emp = employeeRepo.findByEmpCodeAndCompanyName(empCode, companyName).orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        return modelMapper.map(emp, EmployeeDTO.class);
    }
}