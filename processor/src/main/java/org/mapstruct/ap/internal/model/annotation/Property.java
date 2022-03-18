/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public abstract class Property extends ModelElement {

    private final String key;

    Property(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }
}
