package com.employee.EmployeeService.Controller;

import com.employee.EmployeeService.DTO.EmployeeDTO;
import com.employee.EmployeeService.Exception.MissingParameterException;
import com.employee.EmployeeService.Model.Employee;
import com.employee.EmployeeService.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;

    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDto) {
      EmployeeDTO emp =   employeeService.addEmployee(employeeDto);
      return new ResponseEntity<>(emp, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDto, @PathVariable Long id) {
        EmployeeDTO emp = employeeService.updateEmployee(id, employeeDto);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        EmployeeDTO emp = employeeService.getEmployee(id);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){
        List<EmployeeDTO> emp = employeeService.getAllEmployees();
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }
    @GetMapping("/getbyempcodeandcompanyname")
    public ResponseEntity<EmployeeDTO>getEmployeeByEmpCodeAndCompanyName(@RequestParam(required = false) String empCode,
                                                                         @RequestParam(required = false) String companyName){

        List<String>missingParameter = new ArrayList<>();
        if(empCode == null || empCode.trim().isEmpty()){
            missingParameter.add(empCode);
        }
        if(companyName == null || companyName.trim().isEmpty()){
            missingParameter.add(companyName);
        }
        if(!missingParameter.isEmpty()){
            String finalmessage = missingParameter.stream().collect(Collectors.joining(","));
            throw new MissingParameterException("Please Provide "+ finalmessage);
        }
       EmployeeDTO emp = employeeService.getEmployeeByEmpCodeAndCompanyName(empCode,companyName);
        return new ResponseEntity<>(emp, HttpStatus.OK);

    }



}
