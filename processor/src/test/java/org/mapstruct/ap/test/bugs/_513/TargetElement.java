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
public class TargetElement {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) throws MappingException {
        throw new MappingException();
    }

}
