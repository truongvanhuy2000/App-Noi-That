package com.huy.appnoithat.DataModel.WebClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int statusCode;
    private String message;
}
