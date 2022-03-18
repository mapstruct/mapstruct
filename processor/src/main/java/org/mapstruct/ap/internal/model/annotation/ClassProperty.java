/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public class ClassProperty extends Property {

    private List<Type> values;

    public ClassProperty(String key, List<Type> values) {
        super( key );
        this.values = values;
    }

    public List<Type> getValues() {
        return values;
    }

    @Override
    public Set<Type> getImportTypes() {
        return new LinkedHashSet<>( values );
    }
}
