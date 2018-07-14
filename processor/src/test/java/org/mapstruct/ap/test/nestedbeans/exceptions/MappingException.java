/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions;

/**
 * @author Filip Hrisafov
 * @author Darren Rambaud
 */
public class MappingException extends Exception {

    public MappingException(String message, Throwable cause) {
        super( message, cause );
    }
}
