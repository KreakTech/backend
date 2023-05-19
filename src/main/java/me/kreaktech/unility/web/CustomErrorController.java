package me.kreaktech.unility.web;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.kreaktech.unility.exception.ErrorResponse;

@RestController
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        ErrorResponse error;

        switch (httpStatus) {
            case NOT_FOUND:
                error = new ErrorResponse(List.of("Resource not found"), httpStatus);
                break;
            case UNAUTHORIZED:
                error = new ErrorResponse(List.of("Unauthorized access"), httpStatus);
                break;
            default:
                error = new ErrorResponse(List.of("An error occurred"), httpStatus);
                break;
        }

        return new ResponseEntity<>(error, httpStatus);
    }
}
