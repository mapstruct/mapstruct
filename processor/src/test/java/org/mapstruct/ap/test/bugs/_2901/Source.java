/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2901;

import java.util.List;

public class Source {

    private List<String> field;

    public List<String> getField() {
        return field;
    }

    public void setField(List<String> field) {
        this.field = field;
    }
}
