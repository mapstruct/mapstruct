/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.List;

/**
 * @author Ben Zegveld
 */
public class IntegerProperty extends Property {

    private List<Integer> values;

    public IntegerProperty(String key, List<Integer> values) {
        super( key );
        this.values = values;
    }

    public List<Integer> getValues() {
        return values;
    }

}
