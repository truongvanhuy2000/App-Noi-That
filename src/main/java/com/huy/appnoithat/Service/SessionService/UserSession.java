package com.huy.appnoithat.Service.SessionService;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSession {
    @JsonProperty("username")
    private String username;
    @JsonProperty("token")
    private String token;
}
