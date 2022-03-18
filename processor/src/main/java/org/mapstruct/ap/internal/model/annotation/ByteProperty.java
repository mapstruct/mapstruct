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
public class ByteProperty extends Property {

    private List<Byte> values;

    public ByteProperty(String key, List<Byte> values) {
        super( key );
        this.values = values;
    }

    public List<Byte> getValues() {
        return values;
    }

}
