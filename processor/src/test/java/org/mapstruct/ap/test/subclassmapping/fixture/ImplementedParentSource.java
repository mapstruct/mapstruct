/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

public class ImplementedParentSource extends AbstractParentSource {

    private String implementedParentValue;

    public String getImplementedParentValue() {
        return implementedParentValue;
    }

    public void setImplementedParentValue(String implementedParentValue) {
        this.implementedParentValue = implementedParentValue;
    }
}
