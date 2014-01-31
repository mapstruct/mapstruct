/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.HashSet;
import java.util.Set;

import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.ModelElement;

/**
 * A factory method
 *
 * @author Sjaak Derksen
 */
public class FactoryMethod extends ModelElement {

    private final String name;
    private final boolean hasDeclaringMapper;
    private final MapperReference declaringMapper;
    private final Type returnType;

    public FactoryMethod( Method method, MapperReference declaringMapper ) {
        this.name = method.getName();
        this.hasDeclaringMapper = method.getDeclaringMapper() != null;
        this.declaringMapper = declaringMapper;
        this.returnType = method.getReturnType();
    }

    public String getName() {
        return name;
    }

    public Type getDeclaringMapper() {
        return declaringMapper.getMapperType();
    }

    public String getMapperVariableName() {
        return declaringMapper.getVariableName();
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        types.add( returnType );
        types.addAll( returnType.getImportTypes() );
        return types;
    }

    /**
     * There is no declaring mapper when the factory method is on an abstract
     * mapper class. If the declaring mapper is a referenced mapper, then this will
     * result into true.
     *
     * @return true when declaring mapper is a referenced mapper (or factory)
     */
    public boolean isHasDeclaringMapper() {
        return hasDeclaringMapper;
    }

    public Object getReturnType() {
        return returnType;
    }
}
