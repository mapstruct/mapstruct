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
public class DoubleProperty extends Property {

    private List<Double> values;

    public DoubleProperty(String key, List<Double> values) {
        super( key );
        this.values = values;
    }

    public List<Double> getValues() {
        return values;
    }

}
