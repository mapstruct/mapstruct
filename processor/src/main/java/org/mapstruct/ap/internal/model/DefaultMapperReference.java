/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

        String variableName = Strings.getSaveVariableName(
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
