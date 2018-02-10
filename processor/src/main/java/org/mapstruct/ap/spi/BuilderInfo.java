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
 * @author Filip Hrisafov
 */
public class BuilderInfo {

    private final ExecutableElement builderCreationMethod;
    private final ExecutableElement buildMethod;

    private BuilderInfo(ExecutableElement builderCreationMethod, ExecutableElement buildMethod) {
        this.builderCreationMethod = builderCreationMethod;
        this.buildMethod = buildMethod;
    }

    public ExecutableElement getBuilderCreationMethod() {
        return builderCreationMethod;
    }

    public ExecutableElement getBuildMethod() {
        return buildMethod;
    }

    public static class Builder {
        private ExecutableElement builderCreationMethod;
        private ExecutableElement buildMethod;

        public Builder builderCreationMethod(ExecutableElement method) {
            this.builderCreationMethod = method;
            return this;
        }

        public Builder buildMethod(ExecutableElement method) {
            this.buildMethod = method;
            return this;
        }

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
