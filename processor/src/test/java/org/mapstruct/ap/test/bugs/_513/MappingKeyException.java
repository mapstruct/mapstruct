/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._513;

/**
 *
 * @author Sjaak Derksen
 */
public class MappingKeyException extends Exception {

    public MappingKeyException() {
    }

    public MappingKeyException(String msg) {
        super( msg );
    }
}
