package com.huy.appnoithat.DataModel.NtFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectData {
    @JsonProperty("exportData")
    DataPackage dataPackage;
    @JsonProperty("metadata")
    Metadata metadata;
}
