/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Constructor fragment for key mappings for a {@link MapMappingMethod}.
 *
 * @author Oliver Erhart
 */
public class MapKeyMappingConstructorFragment extends ModelElement implements ConstructorFragment {

    private final MapKeyMappingField field;
    private final MappingMethod definingMethod;
    private final List<KeyMappingEntry> keyMappings;

    public MapKeyMappingConstructorFragment(MapKeyMappingField field, MappingMethod definingMethod,
                                            List<KeyMappingEntry> keyMappings) {
        this.field = field;
        this.definingMethod = definingMethod;
        this.keyMappings = keyMappings;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public MapKeyMappingField getField() {
        return field;
    }

    public MappingMethod getDefiningMethod() {
        return definingMethod;
    }

    public List<KeyMappingEntry> getKeyMappings() {
        return keyMappings;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( field == null ) ? 0 : field.hashCode() );
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
        MapKeyMappingConstructorFragment other = (MapKeyMappingConstructorFragment) obj;

        if ( !Objects.equals( field, other.field ) ) {
            return false;
        }
        return true;
    }

    public static void addAllMapKeyMappingFragmentsIn(List<MapMappingMethod> mapMappingMethods,
                                                      Set<ConstructorFragment> targets) {
        for ( MapMappingMethod mapMappingMethod : mapMappingMethods ) {
            MapKeyMappingConstructorFragment fragment = mapMappingMethod.getMapMappingConstructorFragment();
            if ( fragment != null ) {
                targets.add( mapMappingMethod.getMapMappingConstructorFragment() );
            }
        }
    }

    public static class KeyMappingEntry {

        String source;
        String target;

        public KeyMappingEntry(String source, String target) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }
    }

}
