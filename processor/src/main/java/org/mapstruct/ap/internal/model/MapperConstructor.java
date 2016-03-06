/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import static org.mapstruct.ap.internal.util.Strings.join;

/**
 * Represents the constructor of a mapper.
 *
 * @author Sjaak Derksen
 */
public class MapperConstructor extends ModelElement implements Constructor {

    private final String name;
    private final List<Parameter> parameters;
    private final List<Type> thrownTypes;

    public MapperConstructor(String name, Method method) {
        this.name = name;
        this.parameters = method.getParameters();
        this.thrownTypes = method.getThrownTypes();
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();

        for ( Parameter param : parameters ) {
            types.addAll( param.getType().getImportTypes() );
        }

        for ( Type type : thrownTypes ) {
            types.addAll( type.getImportTypes() );
        }

        return types;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Type> getThrownTypes() {
        return thrownTypes;
    }

    @Override
    public String toString() {
        return getName() + "(" + join( parameters, ", " ) + ")";
    }


}
