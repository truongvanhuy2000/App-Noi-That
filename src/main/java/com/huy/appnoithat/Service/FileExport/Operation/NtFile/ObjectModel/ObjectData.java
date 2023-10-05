package com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.huy.appnoithat.DataModel.DataPackage;
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
