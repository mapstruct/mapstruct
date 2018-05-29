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
package org.mapstruct.ap.internal.model.common;

import java.util.HashSet;
import java.util.Set;

public class LocalVariableDefinition extends ModelElement {

    private final Type type;
    private final String name;
    private final ModelElement definition;
    private boolean isUsed = false;

    public LocalVariableDefinition(Type type, String name, ModelElement modelToGenerate) {
        this.type = type;
        this.name = name;
        this.definition = modelToGenerate;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ModelElement getDefinition() {
        return definition;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        if ( isUsed ) {
            types.addAll( type.getImportTypes() );
            types.addAll( definition.getImportTypes() );
        }
        return types;
    }

}
