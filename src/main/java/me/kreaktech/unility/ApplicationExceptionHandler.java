package me.kreaktech.unility;

import lombok.NonNull;
import me.kreaktech.unility.exception.EntityNotFoundException;
import me.kreaktech.unility.exception.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException() {
        ErrorResponse error = new ErrorResponse(List.of("Cannot delete non-existing resource"), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException() {
        ErrorResponse error = new ErrorResponse(List.of("Data Integrity Violation: we cannot process your request."), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @NonNull ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(List.of("Malformed JSON request"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + " " + errorMessage);
        });
        return new ResponseEntity<>(new ErrorResponse(errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        message.append(ex.getMethod());
        message.append(" method is not supported for this request. Supported methods are ");
		if (ex.getSupportedHttpMethods() != null) {
			ex.getSupportedHttpMethods().forEach(t -> message.append(t + " "));
		}
	    ErrorResponse error = new ErrorResponse(List.of(message.toString()), HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(List.of("No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(ex.getMessage()), status);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();
            if (!System.getenv("ENV").equals("prod")) {
                error = new ErrorResponse(Collections.singletonList(stackTrace), status);
            }
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(error, headers, status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName();
		String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
		String actualValue = ex.getValue() != null ? ex.getValue().toString() : "null";
        String errorMessage = String.format("Invalid value '%s' for parameter '%s': must be of type %s", actualValue, fieldName, requiredType);

        ErrorResponse error;
        if (isProduction()) {
            error = new ErrorResponse(Collections.singletonList("An unexpected error has occurred"), HttpStatus.BAD_REQUEST);
        } else {
            error = new ErrorResponse(Collections.singletonList(errorMessage), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList(exception.getMessage()), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception) {
        ErrorResponse error = new ErrorResponse(Collections.singletonList("Unknown error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
        if (!isProduction()) {
            error.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean isProduction() {
        String env = System.getenv("ENV");
        return env != null && env.equals("prod");
    }

}

