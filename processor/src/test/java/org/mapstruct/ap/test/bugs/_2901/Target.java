/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2901;

import java.util.List;

public class Target {

    private List<Integer> field;

    public List<Integer> getField() {
        return field;
    }

    public void setField(List<Integer> field) {
        this.field = field;
    }
}
