package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentFile {
    @JsonProperty("directory")
    private String directory;
    @JsonProperty("time")
    private Long timeStamp;
}
