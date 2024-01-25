package com.revature.jpa.springrestjpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@Table(name = "emp_rest") //if we want table with different name
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private Long phone;

    public Employee(String name, String email, Long phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
