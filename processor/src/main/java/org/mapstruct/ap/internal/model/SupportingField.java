/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.source.builtin.BuiltInFieldReference;

/**
 * supports the
 *
 * @author Sjaak Derksen
 */
public class SupportingField extends Field {

    private final String templateName;
    private final SupportingMappingMethod definingMethod;

    public SupportingField(SupportingMappingMethod definingMethod, BuiltInFieldReference fieldReference, String name) {
        super( fieldReference.getType(), name, true );
        this.templateName = getTemplateNameForClass( fieldReference.getClass() );
        this.definingMethod = definingMethod;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    public SupportingMappingMethod getDefiningMethod() {
        return definingMethod;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( templateName == null ) ? 0 : templateName.hashCode() );
        return result;
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
        SupportingField other = (SupportingField) obj;
        if ( templateName == null ) {
            if ( other.templateName != null ) {
                return false;
            }
        }
        else if ( !templateName.equals( other.templateName ) ) {
            return false;
        }
        return true;
    }

    public static void addAllFieldsIn(Set<SupportingMappingMethod> supportingMappingMethods, Set<Field> targets) {
        for ( SupportingMappingMethod supportingMappingMethod : supportingMappingMethods ) {
            Field field = supportingMappingMethod.getSupportingField();
            if ( field != null ) {
                targets.add( supportingMappingMethod.getSupportingField() );
            }
        }
    }
}
