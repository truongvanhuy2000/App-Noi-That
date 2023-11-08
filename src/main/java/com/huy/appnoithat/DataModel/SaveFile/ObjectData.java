package com.huy.appnoithat.DataModel.SaveFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectData {
    @JsonProperty("exportData")
    List<TabData> exportData;
    @JsonProperty("metadata")
    Metadata metadata;
}
