package com.huy.appnoithat.DataModel;

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


    /**
     * Creates a new RecentFile object with the specified directory and timestamp.
     * If the directory does not contain the ".nt" extension, it is added automatically.
     *
     * @param directory The directory of the recent file.
     * @param timeStamp The timestamp of the recent file.
     */
    public RecentFile(String directory, Long timeStamp) {
        if (!directory.contains(".nt")) {
            directory += ".nt";
        }
        this.directory = directory;
        this.timeStamp = timeStamp;
    }
}
