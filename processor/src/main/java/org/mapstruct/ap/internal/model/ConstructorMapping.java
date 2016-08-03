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

import org.mapstruct.ap.internal.model.common.Constructor;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public class ConstructorMapping extends ModelElement {

    private final Constructor constructor;
    private final LinkedHashMap<String, PropertyMapping> propertyMappings;

    public ConstructorMapping(Constructor constructor, LinkedHashMap<String, PropertyMapping> propertyMappings) {
        this.constructor = constructor;
        this.propertyMappings = propertyMappings;
    }

    public Constructor getConstructor() {
        return constructor;
    }

    public Collection<PropertyMapping> getProperties() {
        return propertyMappings.values();
    }

    @Override
    public Set<Type> getImportTypes() {
        // TODO
        return Collections.emptySet();
    }
}
