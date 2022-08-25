/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2945._target;

public class Target {
    private EnumHolder.Property property;

    public EnumHolder.Property getProperty() {
        return property;
    }

    public void setProperty(EnumHolder.Property property) {
        this.property = property;
    }
}
