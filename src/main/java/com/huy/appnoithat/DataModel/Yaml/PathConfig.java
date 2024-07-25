package com.huy.appnoithat.DataModel.Yaml;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PathConfig {
    @JsonProperty("ProductionRoot")
    private String productionRoot;
}
