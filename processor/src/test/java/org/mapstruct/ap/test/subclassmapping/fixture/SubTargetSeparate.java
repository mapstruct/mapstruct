/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

public class SubTargetSeparate extends ImplementedParentTarget implements InterfaceParentTarget {
    private final String separateValue;

    public SubTargetSeparate(String separateValue) {
        this.separateValue = separateValue;
    }

    public String getSeparateValue() {
        return separateValue;
    }
}
