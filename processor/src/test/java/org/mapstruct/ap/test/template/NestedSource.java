/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.template;

/**
 *
 * @author Sjaak Derksen
 */
public class NestedSource {

    private String nested;

    public NestedSource(String nested) {
        this.nested = nested;
    }

    public String getNested() {
        return nested;
    }

    public void setNested(String nested) {
        this.nested = nested;
    }

}
