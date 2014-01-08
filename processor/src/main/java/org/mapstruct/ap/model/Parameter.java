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

import java.util.Set;

import org.mapstruct.ap.util.Collections;

/**
 * A parameter of a mapping method.
 *
 * @author Gunnar Morling
 */
public class Parameter extends AbstractModelElement {

    private final String name;
    private final Type type;
    private final boolean mappingTarget;

    public Parameter(String name, Type type, boolean mappingTarget) {
        this.name = name;
        this.type = type;
        this.mappingTarget = mappingTarget;
    }

    public Parameter(String name, Type type) {
        this( name, type, false );
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public boolean isMappingTarget() {
        return mappingTarget;
    }

    @Override
    public String toString() {
        return ( mappingTarget ? "@MappingTarget " : "" ) + type.toString() + " " + name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }
}
