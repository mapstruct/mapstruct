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
public class ShortProperty extends Property {

    private List<Short> values;

    public ShortProperty(String key, List<Short> values) {
        super( key );
        this.values = values;
    }

    public List<Short> getValues() {
        return values;
    }

}
