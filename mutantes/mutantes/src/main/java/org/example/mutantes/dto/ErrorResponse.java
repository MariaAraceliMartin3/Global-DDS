package org.example.mutantes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Data

public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}