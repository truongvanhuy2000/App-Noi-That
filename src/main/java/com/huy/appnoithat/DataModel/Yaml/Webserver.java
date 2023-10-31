package com.huy.appnoithat.DataModel.Yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Webserver {
    @JsonProperty("host")
    private String host;
    @JsonProperty("timeout")
    private int timeout;
}
