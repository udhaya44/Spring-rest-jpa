package com.revature.jpa.springrestjpa.serviceTest;

import com.revature.jpa.springrestjpa.exception.ResourceNotFoundException;
import com.revature.jpa.springrestjpa.model.Employee;
import com.revature.jpa.springrestjpa.repos.EmployeeRepository;
import com.revature.jpa.springrestjpa.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void getAllEmployeeTest(){

        //arrange
        Employee employee1 = new Employee("udhaya","udhaya@gmail.com", 9940124180L);
        Employee employee2 = new Employee("aakash","aakash@gmail.com", 1234567890L);
        List<Employee> expectedEmployees = Arrays.asList(employee1, employee2);

        //act
        when(employeeRepository.findAll()).thenReturn(expectedEmployees);
        List<Employee> actualResult = employeeService.getAllEmployee();

        //assert

        assertThat(actualResult).isEqualTo(expectedEmployees);


    }

    @Test
    public void saveEmployeeTest(){
        // Arrange
        Employee employeeToSave = new Employee("John Doe", "john.doe@example.com", 1234567890L);

        // Mock
        when(employeeRepository.save(employeeToSave)).thenReturn(employeeToSave);

        // Act
        Employee savedEmployee = employeeService.saveEmployee(employeeToSave);

        // Assert
        assertThat(savedEmployee).isEqualTo(employeeToSave);
    }

    @Test
    public void deleteEmployeeTest() throws ResourceNotFoundException {
        // Arrange
        long employeeIdToDelete = 1L;

        // Mock
        when(employeeRepository.findById(employeeIdToDelete)).thenReturn(Optional.of(new Employee()));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        // Act
        Map<String, Boolean> response = employeeService.deleteEmployee(employeeIdToDelete);

        // Assert
        assertThat(response.get("deleted")).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void updateEmployeeTest() throws ResourceNotFoundException {
        // Arrange
        long employeeIdToUpdate = 1L;
        Employee existingEmployee = new Employee("John Doe", "john.doe@example.com", 5551234L);

        // Mock
        when(employeeRepository.findById(employeeIdToUpdate)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(employeeIdToUpdate, new Employee("Updated John", "updated.john@example.com", 5555678L));

        // Assert
        assertThat(updatedEmployee.getName()).isEqualTo("Updated John");
        assertThat(updatedEmployee.getEmail()).isEqualTo("updated.john@example.com");
        assertThat(updatedEmployee.getPhone()).isEqualTo(5555678L);

        // Verify that findById and save methods were called
        verify(employeeRepository, times(1)).findById(employeeIdToUpdate);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    public void getEmployeeByNameTest() throws ResourceNotFoundException {
        // Arrange
        String employeeName = "John Doe";
        Employee employee = new Employee(employeeName, "john.doe@example.com", 1234567890L);

        //mock
        when(employeeRepository.findByName(employeeName)).thenReturn(employee);

        // Act
        ResponseEntity<Employee> responseEntity = employeeService.getEmployeeByName(employeeName);

        // Assert
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200); // OK
        assertThat(responseEntity.getBody()).isEqualTo(employee);
    }

    @Test
    public void getEmployeeByIdTest() throws ResourceNotFoundException {
        // Arrange
        long employeeId = 123L;
        Employee employee = new Employee("john", "john.doe@example.com", 1234567890L);

        //mock
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // Act
        ResponseEntity<Employee> responseEntity = employeeService.getEmployeeById(employeeId);

        // Assert
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200); // OK
        assertThat(responseEntity.getBody()).isEqualTo(employee);
    }
}
