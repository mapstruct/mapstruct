/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Mapper reference which is retrieved via the {@code Mappers#getMapper()} method. Used by default if no other component
 * model is specified via {@code Mapper#uses()}.
 *
 * @author Gunnar Morling
 */
public class DefaultMapperReference extends MapperReference {

    private final boolean isAnnotatedMapper;
    private final Set<Type> importTypes;

    private DefaultMapperReference(Type type, boolean isAnnotatedMapper, Set<Type> importTypes, String variableName) {
        super( type, variableName );
        this.isAnnotatedMapper = isAnnotatedMapper;
        this.importTypes = importTypes;
    }

    public static DefaultMapperReference getInstance(Type type, boolean isAnnotatedMapper, TypeFactory typeFactory,
                                                     List<String> otherMapperReferences) {
        Set<Type> importTypes = Collections.asSet( type );
        if ( isAnnotatedMapper ) {
            importTypes.add( typeFactory.getType( "org.mapstruct.factory.Mappers" ) );
        }

        String variableName = Strings.getSafeVariableName(
            type.getName(),
            otherMapperReferences
        );

        return new DefaultMapperReference( type, isAnnotatedMapper, importTypes, variableName );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    public boolean isAnnotatedMapper() {
        return isAnnotatedMapper;
    }
}
