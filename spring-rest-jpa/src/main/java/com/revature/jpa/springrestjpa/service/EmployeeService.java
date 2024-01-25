package com.revature.jpa.springrestjpa.service;

import com.revature.jpa.springrestjpa.exception.ResourceNotFoundException;
import com.revature.jpa.springrestjpa.model.Employee;
import com.revature.jpa.springrestjpa.repos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployee(){
        return employeeRepository.findAll();
    }

    public ResponseEntity<Employee> getEmployeeById(Long id) throws ResourceNotFoundException {
        Employee employee=employeeRepository.findById(id).orElseThrow(()-> new  ResourceNotFoundException("Employee not found with id "+id));
        return ResponseEntity.ok().body(employee);
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Map<String,Boolean> deleteEmployee(long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
        employee.setEmail(employeeDetails.getEmail());
        employee.setName(employeeDetails.getName());
        employee.setPhone(employeeDetails.getPhone());

        return employeeRepository.save(employee);
    }

    public ResponseEntity<Employee> getEmployeeByName(String name) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findByName(name);
        return ResponseEntity.ok().body(employee);
    }
}
