/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.SourceMethod;

/**
 * A reference to another mapper class, which itself may be generated or hand-written.
 *
 * @author Gunnar Morling
 */
public abstract class MapperReference extends Field {

    public MapperReference(Type type, String variableName) {
        super( type, variableName );
    }

    public MapperReference(Type type, String variableName, boolean isUsed) {
        super( type, variableName, isUsed );
    }

    public static MapperReference findMapperReference(List<MapperReference> mapperReferences, SourceMethod method) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                ref.setUsed( ref.isUsed() || !method.isStatic() );
                ref.setTypeRequiresImport( true );
                return ref;
            }
        }
        return null;
    }
}
