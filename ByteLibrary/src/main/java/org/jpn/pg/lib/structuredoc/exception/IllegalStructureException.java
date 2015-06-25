package org.jpn.pg.lib.structuredoc.exception;

import org.jpn.pg.lib.exception.LibraryException;

public class IllegalStructureException extends LibraryException {

    public IllegalStructureException(String message) {
        super(message);
    }

    public IllegalStructureException(String message, Throwable cause) {
        super(message, cause);
    }

}
