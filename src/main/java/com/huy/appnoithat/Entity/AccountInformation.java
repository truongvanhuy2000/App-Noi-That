package com.huy.appnoithat.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AccountInformation {
    private int id;
    private String name;
    private String gender;
    private String email;
    private String address;
    private String phone;
}
