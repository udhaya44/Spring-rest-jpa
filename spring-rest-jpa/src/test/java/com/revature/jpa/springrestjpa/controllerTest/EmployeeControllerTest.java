package com.revature.jpa.springrestjpa.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.jpa.springrestjpa.exception.ResourceNotFoundException;
import com.revature.jpa.springrestjpa.model.Employee;
import com.revature.jpa.springrestjpa.repos.EmployeeRepository;
import com.revature.jpa.springrestjpa.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Captor
    private ArgumentCaptor<Employee> employeeCaptor;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEmployeeWithMockMvc() throws Exception {
        List<Employee> employeeList = new ArrayList<>();

        Employee employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("John Doe");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Jane Doe");

        employeeList.add(employee1);
        employeeList.add(employee2);

        when(employeeService.getAllEmployee()).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/findAllEmployee"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jane Doe"));

        verify(employeeService).getAllEmployee();
    }

    @Test
    public void testGetEmployeeByIdWithMockMvc() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("John Doe");

        Mockito.when(employeeRepository.findById(1L)).thenReturn(java.util.Optional.of(employee));

        mockMvc.perform(MockMvcRequestBuilders.get("/findEmployee/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    public void testSaveEmployeeWithMockMvc() throws Exception {
        Employee employeeToSave = new Employee();
        employeeToSave.setName("John Doe");

        Employee savedEmployee = new Employee();
        savedEmployee.setId(1L);
        savedEmployee.setName("John Doe");

        when(employeeService.saveEmployee(employeeCaptor.capture())).thenReturn(savedEmployee);

        mockMvc.perform(MockMvcRequestBuilders.post("/saveEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeToSave)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));

        verify(employeeService).saveEmployee(employeeCaptor.capture());
        assertThat(employeeCaptor.getValue()).isEqualTo(employeeToSave);
    }

}
