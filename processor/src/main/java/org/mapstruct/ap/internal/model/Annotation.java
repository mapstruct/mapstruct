/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.annotation.Property;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a Java 5 annotation.
 *
 * @author Gunnar Morling
 */
public class Annotation extends ModelElement {

    private final Type type;

    private List<Property> properties;

    public Annotation(Type type) {
        this( type, Collections.emptyList() );
    }

    public Annotation(Type type, List<Property> properties) {
        this.type = type;
        this.properties = properties;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<>();
        for ( Property prop : properties ) {
            types.addAll( prop.getImportTypes() );
        }
        types.add( type );
        return types;
    }

    public List<Property> getProperties() {
        return properties;
    }
}
