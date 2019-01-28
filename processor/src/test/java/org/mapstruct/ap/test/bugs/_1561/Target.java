/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1561;

public class Target {

    private NestedTarget nestedTarget;

    public NestedTarget getNestedTarget() {
        return nestedTarget;
    }

    public void setNestedTarget(NestedTarget nestedTarget) {
        this.nestedTarget = nestedTarget;
    }
}
