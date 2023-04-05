/*
 *
 */

package org.avisto.anonymization.exception;

public class PathException extends FileException{

    public PathException(String message) {
        super(message);
    }

    public PathException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathException(Throwable cause) {
        super(cause);
    }
}
