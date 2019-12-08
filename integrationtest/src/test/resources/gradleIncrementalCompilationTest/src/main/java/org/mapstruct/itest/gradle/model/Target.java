/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.gradle.model;

public class Target {
    private String field = getDefaultValue();
    private String otherField;
    
    public void setField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
    
    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }
    
    public String getOtherField() {
        return otherField;
    }
    
    public String getDefaultValue() {
        return "original";
    }
}
