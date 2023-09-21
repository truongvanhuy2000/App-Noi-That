package com.huy.appnoithat.Service.FileExport.Operation.NtFile.ObjectModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {
    @JsonProperty("fileName")
    String fileName;
    @JsonProperty("dateCreated")
    LocalDate dateCreated;
}
