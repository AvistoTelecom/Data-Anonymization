package org.avisto.anonymization.exception;

public class MethodGenerationException extends RuntimeException{
    public MethodGenerationException(String message, Exception e){
        super(message, e);
    }
    public MethodGenerationException(Exception e){
        super(e);
    }
}
