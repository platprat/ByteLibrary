package org.jpn.pg.lib.exception;

public class LibraryException extends Exception {

    public LibraryException(String message) {
        super(message);
    }

    public LibraryException(String message, Throwable cause) {
        super(message, cause);
    }

}
