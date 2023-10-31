package com.huy.appnoithat.DataModel.Yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Configuration {
    @JsonProperty("Webserver")
    private Webserver Webserver;
}
