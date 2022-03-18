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
public class BooleanProperty extends Property {

    private List<Boolean> values;

    public BooleanProperty(String key, List<Boolean> values) {
        super( key );
        this.values = values;
    }

    public List<Boolean> getValues() {
        return values;
    }

}
