/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.FieldReference;

/**
 * supports the
 *
 * @author Sjaak Derksen
 */
public class SupportingField extends Field {

    private final String templateName;
    private final SupportingMappingMethod definingMethod;

    public SupportingField(SupportingMappingMethod definingMethod, FieldReference fieldReference, String name) {
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

    public static void addAllFieldsIn(Set<SupportingMappingMethod> supportingMappingMethods, Set<Field> targets) {
        for ( SupportingMappingMethod supportingMappingMethod : supportingMappingMethods ) {
            Field field = supportingMappingMethod.getSupportingField();
            if ( field != null ) {
                targets.add( supportingMappingMethod.getSupportingField() );
            }
        }
    }
}
