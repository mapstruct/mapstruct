/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Mapper reference which is retrieved via regular instantiation regardless of the specified component model.
 *
 * @author Christian Bandowski
 */
public class PlainMapperReference extends MapperReference {

    private final Set<Type> importTypes;

    private PlainMapperReference(Type type, Set<Type> importTypes, String variableName) {
        super( type, variableName );
        this.importTypes = importTypes;
    }

    public static PlainMapperReference getInstance(Type type, List<String> otherMapperReferences) {
        Set<Type> importTypes = Collections.asSet( type );

        String variableName = Strings.getSaveVariableName(
            type.getName(),
            otherMapperReferences
        );

        return new PlainMapperReference( type, importTypes, variableName );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }
}
