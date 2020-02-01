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
public class TargetValue {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws MappingException, MappingValueException {
        if ( MappingValueException.class.getSimpleName().equals( value ) ) {
            throw new MappingValueException();
        }
        else if ( MappingException.class.getSimpleName().equals( value ) ) {
            throw new MappingException();
        }
    }

}
