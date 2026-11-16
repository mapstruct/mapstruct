/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.abstractsuperclass;

public class CustomSubclassMappingException extends RuntimeException {
    public CustomSubclassMappingException(String message) {
        super( message );
    }
}
