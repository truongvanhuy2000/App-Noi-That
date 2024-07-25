package com.huy.appnoithat.DataModel.WebClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Response<T> {
    @Builder.Default
    private Optional<ErrorResponse> errorResponse = Optional.empty();
    @Builder.Default
    private Optional<T> response = Optional.empty();
    @Builder.Default
    private Optional<ByteBuffer> rawResponse = Optional.empty();
    private boolean isSuccess;
}
