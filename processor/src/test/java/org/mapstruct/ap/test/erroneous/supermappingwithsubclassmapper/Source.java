/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.supermappingwithsubclassmapper;

public class Source {

    private UnmappableClass property;

    public UnmappableClass getProperty() {
        return property;
    }

    public void setProperty(UnmappableClass property) {
        this.property = property;
    }
}
