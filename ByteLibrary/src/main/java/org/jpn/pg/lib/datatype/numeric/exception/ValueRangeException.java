package org.jpn.pg.lib.datatype.numeric.exception;

import org.jpn.pg.lib.exception.LibraryException;

public class ValueRangeException extends LibraryException {

    public ValueRangeException(String message) {
        super(message);
    }

    public ValueRangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
