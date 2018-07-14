/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import java.util.LinkedList;

/**
 * @author Sjaak Derksen
 */
public class CustomListImpl<T> extends LinkedList<T> implements CustomList<T> {

    private final String typeProp;

    public CustomListImpl(String typeProp) {
        this.typeProp = typeProp;
    }

    @Override
    public String getTypeProp() {
        return typeProp;
    }
}
