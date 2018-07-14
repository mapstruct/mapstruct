/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._775;

/**
 * @author Andreas Gudian
 */
public class IterableContainer {
    private Iterable<? extends Integer> values;

    public void setValues(Iterable<? extends Integer> values) {
        this.values = values;
    }

    public Iterable<? extends Integer> getValues() {
        return values;
    }
}
