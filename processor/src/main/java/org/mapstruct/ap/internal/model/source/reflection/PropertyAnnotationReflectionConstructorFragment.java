/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.reflection;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;

public class PropertyAnnotationReflectionConstructorFragment implements ConstructorFragment {

    private final List<PropertyAnnotationReflection> fieldInitializers = new ArrayList<>();

    public void addField(PropertyAnnotationReflectionField field) {
        fieldInitializers.add( new PropertyAnnotationReflection( field ) );
    }

    public List<PropertyAnnotationReflection> getFieldInitializers() {
        return fieldInitializers;
    }
}
