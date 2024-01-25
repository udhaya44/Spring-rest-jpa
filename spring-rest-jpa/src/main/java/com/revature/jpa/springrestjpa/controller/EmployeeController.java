package com.revature.jpa.springrestjpa.controller;

import com.revature.jpa.springrestjpa.exception.ResourceNotFoundException;
import com.revature.jpa.springrestjpa.model.Employee;
import com.revature.jpa.springrestjpa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/findAllEmployee")
    public List<Employee> getAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @GetMapping("/findEmployee/{id}")
    public ResponseEntity<Employee> getEmployeeById( @PathVariable long id) throws ResourceNotFoundException{
        return employeeService.getEmployeeById(id);
    }

    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
                                                   @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping("/deleteEmployee/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable long id)  throws ResourceNotFoundException {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/employeebyname/{name}")
    public ResponseEntity<Employee> getEmployeeByname( @PathVariable String name) throws ResourceNotFoundException{
        return employeeService.getEmployeeByName(name);
    }
}
