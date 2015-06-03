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
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a Java 5 annotation.
 *
 * @author Gunnar Morling
 */
public class Annotation extends ModelElement {

    private final Type type;

    /**
     * List of annotation attributes. Quite simplistic, but it's sufficient for now.
     */
    private List<String> properties;

    public Annotation(Type type) {
        this( type, Collections.<String>emptyList() );
    }

    public Annotation(Type type, List<String> properties) {
        this.type = type;
        this.properties = properties;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.singleton( type );
    }

    public List<String> getProperties() {
        return properties;
    }
}
