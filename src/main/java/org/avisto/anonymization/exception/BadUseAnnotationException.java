package org.avisto.anonymization.exception;

public class BadUseAnnotationException extends GenericDataAnonymisationException {
    public BadUseAnnotationException(String message) {
        super(message);
    }

    public BadUseAnnotationException(String message, Throwable e) {
        super(message, e);
    }

    public BadUseAnnotationException(Throwable e) {
        super(e);
    }

}
