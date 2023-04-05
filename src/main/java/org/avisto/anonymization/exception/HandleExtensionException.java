/*
 *
 */

package org.avisto.anonymization.exception;

public class HandleExtensionException extends FileException{

    public HandleExtensionException(String message) {
        super(message);
    }

    public HandleExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleExtensionException(Throwable cause) {
        super(cause);
    }
}
