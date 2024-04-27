/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import java.util.List;

public class Target {
    private List<TargetChild> nestedTargetChild;
    private TargetChild nestedSecondTargetChild;
    private String otherField;

    public TargetChild getNestedSecondTargetChild() {
        return nestedSecondTargetChild;
    }

    public void setNestedSecondTargetChild(TargetChild nestedSecondTargetChild) {
        this.nestedSecondTargetChild = nestedSecondTargetChild;
    }

    public List<TargetChild> getNestedTargetChild() {
        return nestedTargetChild;
    }

    public void setNestedTargetChild(List<TargetChild> nestedTargetChild) {
        this.nestedTargetChild = nestedTargetChild;
    }

    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }
}
