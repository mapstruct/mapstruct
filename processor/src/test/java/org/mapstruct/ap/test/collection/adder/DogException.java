/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

/**
 * @author Sjaak Derksen
 */
public class DogException extends Exception {

    private static final long serialVersionUID = 1L;

    public DogException() {
    }

    public DogException(String msg) {
        super( msg );
    }
}
