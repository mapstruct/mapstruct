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
