/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import java.util.HashMap;

/**
 * @author Sjaak Derksen
 */
public class CustomMapImpl<K, V> extends HashMap<K, V> implements CustomMap<K, V> {

    private final String typeProp;

    public CustomMapImpl(String typeProp) {
        this.typeProp = typeProp;
    }

    @Override
    public String getTypeProp() {
        return typeProp;
    }
}
