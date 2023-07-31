package com.huy.appnoithat.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private int id;
    private String username;
    private String password;
    private int active;
    private int info_id;
}
