package org.mapstruct.ap.internal.model.annotation;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

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
