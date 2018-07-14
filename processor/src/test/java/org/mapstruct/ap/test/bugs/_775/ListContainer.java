/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._775;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas Gudian
 */
public class ListContainer {
    private List<String> values = new ArrayList<String>();

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
