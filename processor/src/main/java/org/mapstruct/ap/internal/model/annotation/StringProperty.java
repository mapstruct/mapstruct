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
public class StringProperty extends Property {

    private List<String> values;

    public StringProperty(String key, List<String> values) {
        super( key );
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

}
