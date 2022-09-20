/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import java.util.Map;

public class IncompatibleTarget {

    private Map<Integer, Void> updatedOn;

    public Map<Integer, Void> getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Map<Integer, Void> updatedOn) {
        this.updatedOn = updatedOn;
    }

}
