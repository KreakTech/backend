package me.kreaktech.unility.exception;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;
    private HttpStatus status;
    private List<String> message;
    private String stackTrace;

    public ErrorResponse(List<String> message, HttpStatus status) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
        this.status = status;
    }
}
