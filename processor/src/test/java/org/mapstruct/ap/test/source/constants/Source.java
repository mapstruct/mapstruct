/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private String propertyThatShouldBeMapped;

    public String getPropertyThatShouldBeMapped() {
        return propertyThatShouldBeMapped;
    }

    public void setPropertyThatShouldBeMapped(String propertyThatShouldBeMapped) {
        this.propertyThatShouldBeMapped = propertyThatShouldBeMapped;
    }
}
