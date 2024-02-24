package com.huy.appnoithat.DataModel.Enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum FileType {
    PDF("pdf"),
    EXCEL("xlsx");
    public final String extension;
}
