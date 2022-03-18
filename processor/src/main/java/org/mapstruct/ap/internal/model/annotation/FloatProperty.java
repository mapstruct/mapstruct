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
public class FloatProperty extends Property {

    private List<Float> values;

    public FloatProperty(String key, List<Float> values) {
        super( key );
        this.values = values;
    }

    public List<Float> getValues() {
        return values;
    }

}
