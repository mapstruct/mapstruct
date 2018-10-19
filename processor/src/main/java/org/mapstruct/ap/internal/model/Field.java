/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * A field of a mapper type, e.g. a reference to another mapper.
 *
 * @author Gunnar Morling
 */
public class Field extends ModelElement {

    private final Type type;
    private final String variableName;
    private boolean used;
    private boolean typeRequiresImport;

    public Field(Type type, String variableName, boolean used) {
        this.type = type;
        this.variableName = variableName;
        this.used = used;
        this.typeRequiresImport = used;
    }

    public Field(Type type, String variableName) {
        this.type = type;
        this.variableName = variableName;
        this.used = false;
        this.typeRequiresImport = false;
    }

    /**
     * Returns the type of this field.
     *
     * @return the type of this field
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the variable name of this field.
     *
     * @return the variable name of this reference
     */
    public String getVariableName() {
        return variableName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    /**
     * indicates whether the field is indeed used
     * @return true when field is used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * sets the field as being used
     * @param isUsed must be true when being used.
     */
    public void setUsed(boolean isUsed) {
        this.used = isUsed;
    }

    /**
     * field needs to be imported
     * @return true if the type should be included in the import of the generated type
     */
    public boolean isTypeRequiresImport() {
        return typeRequiresImport;
    }

    /**
     * set field needs to be imported
     * @param typeRequiresImport needs to be imported
     */
    public void setTypeRequiresImport(boolean typeRequiresImport) {
        this.typeRequiresImport = typeRequiresImport;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.variableName != null ? this.variableName.hashCode() : 0);
        return hash;
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
        final Field other = (Field) obj;
        return !( (this.variableName == null) ?
            (other.variableName != null) : !this.variableName.equals( other.variableName ) );
    }

    public static List<String> getFieldNames(Set<Field> fields) {
        List<String> names = new ArrayList<>( fields.size() );
        for ( Field field : fields ) {
            names.add( field.getVariableName() );
        }
        return names;
    }

}
