package com.huy.appnoithat.DataModel.Enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FileType {
    PDF("pdf"),
    EXCEL("xlsx"),
    NT("nt");
    public final String extension;
}
