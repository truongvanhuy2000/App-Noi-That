package com.huy.appnoithat.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private int id;
    private String username;
    private String password;
    private int active;
    private AccountInformation accountInformation;

}
