/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jaxb;

public class SuperTypeDto {
    private String inheritedCamelCase;
    private String inheritedUnderscore;

    public String getInheritedCamelCase() {
        return inheritedCamelCase;
    }

    public void setInheritedCamelCase(String inheritedCamelCase) {
        this.inheritedCamelCase = inheritedCamelCase;
    }

    public String getInheritedUnderscore() {
        return inheritedUnderscore;
    }

    public void setInheritedUnderscore(String inheritedUnderscore) {
        this.inheritedUnderscore = inheritedUnderscore;
    }
}
