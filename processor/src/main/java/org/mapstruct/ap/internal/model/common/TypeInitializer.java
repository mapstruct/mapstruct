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

import javax.lang.model.element.ExecutableElement;

/**
 * Initializes a {@link Type} for mapping.  In the case of a builder, this initializer will product an intermediate
 * {@link Type} used during the mapping process
 */
public class TypeInitializer {

    /**
     * The type that contains the initializer method.  The initializer could be invoked as a static method, or as
     * a constructor.
     */
    private final Type enclosingType;
    private final ExecutableElement initializerMethod;
    private final Type initializedType;

    public TypeInitializer(Type initializedType, Type enclosingType, ExecutableElement initializerMethod) {
        this.initializedType = initializedType;
        this.initializerMethod = initializerMethod;
        this.enclosingType = enclosingType;
    }

    public ExecutableElement getInitializerMethod() {
        return initializerMethod;
    }

    public Type getInitializedType() {
        return initializedType;
    }

    public Type getEnclosingType() {
        return enclosingType;
    }

}
