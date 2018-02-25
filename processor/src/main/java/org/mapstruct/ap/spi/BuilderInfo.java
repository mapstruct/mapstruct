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
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/**
 * Holder for the builder information.
 *
 * @author Filip Hrisafov
 */
public class BuilderInfo {

    private final ExecutableElement builderCreationMethod;
    private final ExecutableElement buildMethod;

    private BuilderInfo(ExecutableElement builderCreationMethod, ExecutableElement buildMethod) {
        this.builderCreationMethod = builderCreationMethod;
        this.buildMethod = buildMethod;
    }

    /**
     * The method that can be used for instantiating a builder. This can be:
     * <ul>
     * <li>A {@code public static} method in the type being build</li>
     * <li>A {@code public static} method in the builder itself</li>
     * <li>The default constructor of the builder</li>
     * </ul>
     *
     * @return the creation method for the builder
     */
    public ExecutableElement getBuilderCreationMethod() {
        return builderCreationMethod;
    }

    /**
     * The method that can be used to build the type being built.
     * This should be a {@code public} method within the builder itself
     *
     * @return the build method for the type
     */
    public ExecutableElement getBuildMethod() {
        return buildMethod;
    }

    public static class Builder {
        private ExecutableElement builderCreationMethod;
        private ExecutableElement buildMethod;

        /**
         * @see BuilderInfo#getBuilderCreationMethod()
         */
        public Builder builderCreationMethod(ExecutableElement method) {
            this.builderCreationMethod = method;
            return this;
        }

        /**
         * @see BuilderInfo#getBuildMethod()
         */
        public Builder buildMethod(ExecutableElement method) {
            this.buildMethod = method;
            return this;
        }

        /**
         * Create the {@link BuilderInfo}.
         * @throws IllegalArgumentException if the builder creation or build methods are {@code null}
         */
        public BuilderInfo build() {
            if ( builderCreationMethod == null ) {
                throw new IllegalArgumentException( "Builder creation method is mandatory" );
            }
            else if ( buildMethod == null ) {
                throw new IllegalArgumentException( "Build method is mandatory" );
            }
            return new BuilderInfo( builderCreationMethod, buildMethod );
        }
    }
}
