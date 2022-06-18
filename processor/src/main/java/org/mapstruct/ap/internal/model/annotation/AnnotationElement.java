/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public class AnnotationElement extends ModelElement {
    public enum AnnotationElementType {
        BOOLEAN, BYTE, CHARACTER, CLASS, DOUBLE, ENUM, FLOAT, INTEGER, LONG, SHORT, STRING
    }

    private final String elementName;
    private final List<? extends Object> values;
    private final AnnotationElementType type;

    public AnnotationElement(AnnotationElementType type, List<? extends Object> values) {
        this( type, null, values );
    }

    public AnnotationElement(AnnotationElementType type, String elementName, List<? extends Object> values) {
        this.type = type;
        this.elementName = elementName;
        this.values = values;
    }

    public String getElementName() {
        return elementName;
    }

    public List<? extends Object> getValues() {
        return values;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = null;
        for ( Object value : values ) {
            if ( value instanceof ModelElement ) {
                if ( importTypes == null ) {
                    importTypes = new HashSet<>();
                }
                importTypes.addAll( ( (ModelElement) value ).getImportTypes() );
            }
        }

        return importTypes == null ? Collections.emptySet() : importTypes;
    }

    public boolean isBoolean() {
        return type == AnnotationElementType.BOOLEAN;
    }

    public boolean isByte() {
        return type == AnnotationElementType.BYTE;
    }

    public boolean isCharacter() {
        return type == AnnotationElementType.CHARACTER;
    }

    public boolean isClass() {
        return type == AnnotationElementType.CLASS;
    }

    public boolean isDouble() {
        return type == AnnotationElementType.DOUBLE;
    }

    public boolean isEnum() {
        return type == AnnotationElementType.ENUM;
    }

    public boolean isFloat() {
        return type == AnnotationElementType.FLOAT;
    }

    public boolean isInteger() {
        return type == AnnotationElementType.INTEGER;
    }

    public boolean isLong() {
        return type == AnnotationElementType.LONG;
    }

    public boolean isShort() {
        return type == AnnotationElementType.SHORT;
    }

    public boolean isString() {
        return type == AnnotationElementType.STRING;
    }

    @Override
    public int hashCode() {
        return Objects.hash( elementName, type, values );
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        AnnotationElement other = (AnnotationElement) obj;
        return Objects.equals( elementName, other.elementName )
            && type == other.type
            && Objects.equals( values, other.values );
    }

}
