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

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents the constructor of a decorator.
 *
 * @author Gunnar Morling
 */
public class DecoratorConstructor extends ModelElement implements Constructor {

    private final String name;
    private final String delegateName;
    private final boolean invokeSuperConstructor;

    public DecoratorConstructor(String name, String delegateName, boolean invokeSuperConstructor) {
        this.name = name;
        this.delegateName = delegateName;
        this.invokeSuperConstructor = invokeSuperConstructor;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDelegateName() {
        return delegateName;
    }

    public boolean isInvokeSuperConstructor() {
        return invokeSuperConstructor;
    }
}
