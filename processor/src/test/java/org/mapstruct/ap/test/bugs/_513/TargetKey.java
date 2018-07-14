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
public class TargetKey {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws MappingException, MappingKeyException {
        if ( MappingKeyException.class.getSimpleName().equals( value ) ) {
            throw new MappingKeyException();
        }
        else if ( MappingException.class.getSimpleName().equals( value ) ) {
            throw new MappingException();
        }
    }

}
