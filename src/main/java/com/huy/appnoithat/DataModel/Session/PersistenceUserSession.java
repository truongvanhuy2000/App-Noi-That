package com.huy.appnoithat.DataModel.Session;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersistenceUserSession {
    @JsonProperty("token")
    private String token;
    @JsonProperty("refreshToken")
    private String refreshToken;
}
