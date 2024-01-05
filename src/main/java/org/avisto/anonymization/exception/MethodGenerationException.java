package org.avisto.anonymization.exception;

public class MethodGenerationException extends GenericDataAnonymisationException {
    public MethodGenerationException(String message, Exception e){
        super(message, e);
    }
    public MethodGenerationException(Exception e){
        super(e);
    }
}
