package com.huy.appnoithat.DataModel.SaveFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.DataModel.DataPackage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabData {
    @JsonProperty("dataPackage")
    DataPackage dataPackage;
    @JsonProperty("tabName")
    String tabName;
}
