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

    private static final String BUILD_METHOD_DEFAULT_VALUE = "build";
    private static final String BUILDER_FACTORY_METHOD_DEFAULT_VALUE = "builder";

    /**
     * The name of the method on the builder type that's used to produce the immutable instance.
     */
    private final String buildMethod;

    /**
     * The name of a static method on the containing class that produces a new instance of the builder.
     *
     * @todo Allow builder to be provided with an {@link org.mapstruct.ObjectFactory} or support constructors
     * <p>
     * <pre>
     *     ImmutablePerson.builder();
     * </pre>
     */
    private final String staticBuilderFactoryMethod;

    public BuilderOptions() {
        this.buildMethod = null;
        this.staticBuilderFactoryMethod = null;
    }

    public BuilderOptions(String buildMethod, String staticBuilderFactoryMethod) {
        this.buildMethod = buildMethod;
        this.staticBuilderFactoryMethod = staticBuilderFactoryMethod;
    }

    public String getBuildMethod() {
        return firstNonNull( buildMethod, BUILD_METHOD_DEFAULT_VALUE );
    }

    public String getStaticBuilderFactoryMethod() {
        return firstNonNull( staticBuilderFactoryMethod, BUILDER_FACTORY_METHOD_DEFAULT_VALUE );
    }

    /**
     * Because @BuilderOptions annotation can be applied to either side (building or target), this function allows
     * us to return an immutable copy of the current BuilderOptions with any modifications.  Also detects any
     * incompatible configuration (in the case that configuration exists on both the builder and target classes).
     *
     * @param buildMethod New value for buildMethod.  Null value is accepted, but won't overwrite an existing value.
     * @param staticBuilderFactoryMethod New value for staticBuilderFactoryMethod.  Null value is accepted, but won't
     *                                   overwrite an existing value.
     * @return An immutable copy of this instance, with new values.
     */
    public BuilderOptions with(String buildMethod, String staticBuilderFactoryMethod) {
        if ( this.buildMethod != null && !this.buildMethod.equals( buildMethod ) ) {
            //todo:ericm Best way to surface these errors?
            throw new IllegalArgumentException( "Conflicting builder configuration for buildMethod: " +
                this.buildMethod + " != " + buildMethod );
        }
        if ( this.staticBuilderFactoryMethod != null &&
            !this.staticBuilderFactoryMethod.equals( staticBuilderFactoryMethod ) ) {
            throw new IllegalArgumentException( "Conflicting builder configuration for staticBuilderFactoryMethod: " +
                this.staticBuilderFactoryMethod + " != " + staticBuilderFactoryMethod );
        }
        return new BuilderOptions(
            firstNonNull( this.buildMethod, buildMethod ),
            firstNonNull( this.staticBuilderFactoryMethod, staticBuilderFactoryMethod )
        );
    }

    /**
     * Alternative to Objects.toString for language level.  Maybe something else exists?
     */
    private String firstNonNull(String a, String b) {
        return a != null ? a : b;
    }
}
