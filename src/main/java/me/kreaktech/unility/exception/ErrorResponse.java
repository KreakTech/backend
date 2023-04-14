package me.kreaktech.unility.exception;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;
    private List<String> message;

    public ErrorResponse(List<String> message) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMessage() {
        return this.message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
