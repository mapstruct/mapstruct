/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

public class Target {

    private String property;
    private String nameBasedSource;
    private UnmappableClass constant;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getNameBasedSource() {
        return nameBasedSource;
    }

    public void setNameBasedSource(String nameBasedSource) {
        this.nameBasedSource = nameBasedSource;
    }

    public UnmappableClass getConstant() {
        return constant;
    }

    public void setConstant(UnmappableClass constant) {
        this.constant = constant;
    }

}
