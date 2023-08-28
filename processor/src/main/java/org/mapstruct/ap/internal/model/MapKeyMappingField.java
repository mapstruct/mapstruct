/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Field that is used for mapping keys of a {@link MapMappingMethod}.
 *
 * @author Oliver Erhart
 */
public class MapKeyMappingField extends Field {

    public MapKeyMappingField(Type type, String variableName) {
        super( type, variableName, true );
    }

    public static void addAllMapKeyMappingFieldsIn(List<MapMappingMethod> mapMappingMethods, Set<Field> targets) {
        for ( MapMappingMethod mapMappingMethod : mapMappingMethods ) {
            MapKeyMappingConstructorFragment fragment = mapMappingMethod.getMapMappingConstructorFragment();
            if (fragment != null) {
                Field field = fragment.getField();
                if ( field != null ) {
                    targets.add( field );
                }
            }
        }
    }

    @Override
    protected String getTemplateName() {
        return getTemplateNameForClass( Field.class );
    }
}
