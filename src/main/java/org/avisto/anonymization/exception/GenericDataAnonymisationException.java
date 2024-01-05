package org.avisto.anonymization.exception;

public class GenericDataAnonymisationException extends RuntimeException {
    public GenericDataAnonymisationException(String message) {
        super(message);
    }
    public GenericDataAnonymisationException(String message, Throwable e) {
        super(message, e);
    }
    public GenericDataAnonymisationException(Throwable e) {
        super(e);
    }
}
