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

/**
 * For immutable classes that are created using a builder, this class stores metadata about the builder implementation.
 *
 * @see Type#builderOptions
 */
public class BuilderOptions {

    /**
     * The type that will be used to build.
     */
    private final Type builderType;

    /**
     * The name of the method on the {@link #builderType} that's used to produce the immutable instance.
     */
    private final String buildMethod;

    /**
     * The name of a static method on the containing class that produces a new instance of the builder.
     *
     * @todo Allow builder to be provided with an {@link org.mapstruct.ObjectFactory} or support constructors
     *
     * <pre>
     *     ImmutablePerson.builder();
     * </pre>
     */
    private final String staticBuilderFactoryMethod;

    public BuilderOptions(Type builderType, String buildMethod, String staticBuilderFactoryMethod) {
        this.builderType = builderType;
        this.buildMethod = buildMethod;
        this.staticBuilderFactoryMethod = staticBuilderFactoryMethod;
    }

    public String getBuildMethod() {
        return buildMethod;
    }

    public Type getBuilderType() {
        return builderType;
    }

    public String getStaticBuilderFactoryMethod() {
        return staticBuilderFactoryMethod;
    }
}
