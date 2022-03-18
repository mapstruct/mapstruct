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
public class LongProperty extends Property {

    private List<Long> values;

    public LongProperty(String key, List<Long> values) {
        super( key );
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }

}
