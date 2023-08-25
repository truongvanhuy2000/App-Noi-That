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
    private String roles;
    private AccountInformation accountInformation;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles='" + roles + '\'' +
                ", accountInformation=" + accountInformation +
                '}';
    }
}
