package com.huy.appnoithat.Controller.FileNoiThatExplorer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecentFile {
    @JsonProperty("directory")
    private String directory;
    @JsonProperty("time")
    private Long timeStamp;

    public RecentFile(String directory, Long timeStamp) {
        if (!directory.contains(".nt")) {
            directory += ".nt";
        }
        this.directory = directory;
        this.timeStamp = timeStamp;
    }
}
