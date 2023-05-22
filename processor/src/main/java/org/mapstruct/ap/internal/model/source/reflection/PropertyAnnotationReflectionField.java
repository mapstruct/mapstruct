/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.reflection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.common.SourcePropertyReflectionInfo;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;

public class PropertyAnnotationReflectionField extends Field {

    private final SourcePropertyReflectionInfo reflectionInfo;
    private final String templateName;

    private PropertyAnnotationReflectionField(Type type, String fieldName,
                                              SourcePropertyReflectionInfo reflectionInfo) {
        super( type, fieldName, true );
        this.templateName = getTemplateNameForClass( Field.class );
        this.reflectionInfo = reflectionInfo;
    }

    @Override
    protected String getTemplateName() {
        return templateName;
    }

    public SourcePropertyReflectionInfo getReflectionInfo() {
        return reflectionInfo;
    }

    public static PropertyAnnotationReflectionField getSafeField(
        Type type,
        SourcePropertyReflectionInfo reflectionInfo,
        Set<PropertyAnnotationReflectionField> existingFields) {

        if ( reflectionInfo == null ) {
            return null;
        }

        List<String> existingFieldNames = new ArrayList<>();
        for ( PropertyAnnotationReflectionField existingField : existingFields ) {
            if ( existingField.getType().equals( type )
                && existingField.getReflectionInfo().equals( reflectionInfo ) ) {
                // field already exists, use that one
                return existingField;
            }
            existingFieldNames.add( existingField.getVariableName() );
        }

        String preferredFieldName = Strings.decapitalize( Strings.joinAndCamelize( Arrays.asList(
            reflectionInfo.getContainingType().getName(),
            reflectionInfo.getPropertyName(),
            type.getName(),
            "Annotation"
        ) ) );

        String name = Strings.getSafeVariableName( preferredFieldName, existingFieldNames );
        return new PropertyAnnotationReflectionField( type, name, reflectionInfo );
    }

}
