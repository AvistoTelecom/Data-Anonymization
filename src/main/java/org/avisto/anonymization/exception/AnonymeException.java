package org.avisto.anonymization.exception;

public class AnonymeException extends RuntimeException{
    public AnonymeException(String message) {
        super(message);
    }

    public AnonymeException(String message, Throwable e) {
        super(message, e);
    }

    public AnonymeException(Throwable e) {
        super(e);
    }
}
