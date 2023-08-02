package com.huy.appnoithat.Entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AccountInformation {
    private int id;
    private String name;
    private String gender;
    private String email;
    private String address;
    private String phone;


    @Override
    public String toString() {
        return "AccountInformation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
