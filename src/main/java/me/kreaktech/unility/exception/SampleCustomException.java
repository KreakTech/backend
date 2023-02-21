package me.kreaktech.unility.exception;

public class SampleCustomException extends RuntimeException { 

    public SampleCustomException(Long studentId, Long courseId) {
        super("This is a sample exception");
    }
    
}