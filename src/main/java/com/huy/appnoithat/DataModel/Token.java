package com.huy.appnoithat.DataModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {
    @JsonProperty("token")
    private String token;
    @JsonProperty("refreshToken")
    private String refreshToken;
}
