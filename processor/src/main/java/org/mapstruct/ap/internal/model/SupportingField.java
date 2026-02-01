/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.util.Strings;

/**
 * supports the
 *
 * @author Sjaak Derksen
 */
public class SupportingField extends Field {

    private final String templateName;
    private final Map<String, Object> templateParameter;

    private final SupportingMappingMethod definingMethod;

    public SupportingField(SupportingMappingMethod definingMethod, FieldReference fieldReference, String name) {
        super( fieldReference.getType(), name, true );
        this.templateName = getTemplateNameForClass( fieldReference.getClass() );
        this.templateParameter = fieldReference.getTemplateParameter();
        this.definingMethod = definingMethod;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    public Map<String, Object> getTemplateParameter() {
        return templateParameter;
    }

    public SupportingMappingMethod getDefiningMethod() {
        return definingMethod;
    }

    public static void addAllFieldsIn(Set<SupportingMappingMethod> supportingMappingMethods, Set<Field> targets) {
        for ( SupportingMappingMethod supportingMappingMethod : supportingMappingMethods ) {
            Field field = supportingMappingMethod.getSupportingField();
            if ( field != null ) {
                targets.add( field );
            }
        }
    }

    public static Field getSafeField(SupportingMappingMethod method, FieldReference ref, Set<Field> existingFields) {
        if ( ref == null ) {
            return null;
        }

        String name = ref.getVariableName();
        for ( Field existingField : existingFields ) {
            if ( existingField.getVariableName().equals( ref.getVariableName() )
                && existingField.getType().equals( ref.getType() ) ) {
                // field already exists, use that one
                return existingField;
            }
        }
        name = Strings.getSafeVariableName( name, Field.getFieldNames( existingFields ) );
        return new SupportingField( method, ref, name );
    }
}
