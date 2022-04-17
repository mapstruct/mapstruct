/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

public class SubSourceOverride extends ImplementedParentSource implements InterfaceParentSource {
    private final String finalValue;

    public SubSourceOverride(String finalValue) {
        this.finalValue = finalValue;
    }

    public String getFinalValue() {
        return finalValue;
    }
}
