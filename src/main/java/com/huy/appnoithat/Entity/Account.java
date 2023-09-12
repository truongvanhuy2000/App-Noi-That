package com.huy.appnoithat.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @JsonProperty("id")
    private int id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("active")
    private boolean active;
    @JsonProperty("accountInformation")
    private AccountInformation accountInformation;
    @JsonProperty("roles")
    private List<String> roleList;
    @JsonProperty("enabled")
    private boolean enabled;
    @JsonProperty("expiredDate")
    private Date expiredDate;
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", accountInformation=" + accountInformation +
                '}';
    }
}
