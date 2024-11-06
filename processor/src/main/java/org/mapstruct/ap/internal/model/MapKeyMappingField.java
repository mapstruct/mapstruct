/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;

/**
 * Field that is used for mapping keys of a {@link MapMappingMethod}.
 *
 * @author Oliver Erhart
 */
public class MapKeyMappingField extends Field {

    public MapKeyMappingField(String variableName) {
        super( null, variableName, true );
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

}
