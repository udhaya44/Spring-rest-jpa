package com.revature.jpa.springrestjpa.repoTest;
import com.revature.jpa.springrestjpa.model.Employee;
import com.revature.jpa.springrestjpa.repos.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testFindByName() {
        // Given
        String employeeName = "John Doe";
        Employee employee = new Employee();
        employee.setName(employeeName);
        employeeRepository.save(employee);

        // When
        Employee foundEmployee = employeeRepository.findByName(employeeName);

        // Then
        assertNotNull(foundEmployee);
        assertEquals(employeeName, foundEmployee.getName());
    }
}
