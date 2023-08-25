package com.huy.appnoithat.DataModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class Employee {
        private String name;
        private int age;
        private Double payment;
        private Double bonus;
        private Date birthDate;
}
