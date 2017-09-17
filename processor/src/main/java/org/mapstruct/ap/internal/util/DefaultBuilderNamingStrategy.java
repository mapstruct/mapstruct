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
package org.mapstruct.ap.internal.util;

import static java.beans.Introspector.decapitalize;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.BuilderNamingStrategy;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.MethodType;


/**
 * The default implementation of {@link BuilderNamingStrategy} that assumes the builder won't have getters, and that
 * setters are prefixed with either "set" or "with", and adder methods are prefixed with "add"
 *
 * @author Eric Martineau
 */
public class DefaultBuilderNamingStrategy implements BuilderNamingStrategy {

    private final BuilderProvider builderProvider;

    public DefaultBuilderNamingStrategy() {
        this( Services.get( BuilderProvider.class, new DefaultBuilderProvider() ) );
    }

    public DefaultBuilderNamingStrategy(BuilderProvider provider) {
        this.builderProvider = provider;
    }

    /**
     * Determines the type of method, assuming the method is for a builder.
     *
     * The only values returned are {@link MethodType#OTHER}, {@link MethodType#ADDER} or {@link MethodType#SETTER}
     * @param type The concrete class for which we are examining the method
     * @param method to be analyzed.
     * @param elements
     * @param types
     */
    public MethodType getMethodType(TypeElement type, ExecutableElement method, Elements elements, Types types) {
        final BuilderInfo builderInfo = getBuilderMapping( type, method, elements, types );
        if ( builderInfo != null ) {
            final String methodName = method.getSimpleName().toString();
            if ( builderInfo.getFinalizeMethod().getSimpleName().contentEquals( method.getSimpleName() ) ) {
                return MethodType.OTHER;
            }
            else if ( methodName.startsWith( "add" ) ) {
                return MethodType.ADDER;
            }
            else {
                return MethodType.SETTER;
            }
        }
        return MethodType.OTHER;
    }

    /**
     * Analyzes the method and derives the property name.  Currently supports prefix of "with", "set", or no prefix.
     * Builders are used for writing, no getters are supported.
     *
     * @param forType The concrete type this method is being inspected for
     * @param setterMethod The setter method
     *
     * @return the property name.
     */
    @Override
    public String getPropertyName(TypeElement forType, ExecutableElement setterMethod, Elements elements,
                                  Types types) {
        String methodName = setterMethod.getSimpleName().toString();
        if ( methodName.startsWith( "with" ) ) {
            return decapitalize( methodName.substring( 4 ) );
        }
        else if ( methodName.startsWith( "set" ) ) {
            return decapitalize( methodName.substring( 3 ) );
        }
        else {
            return methodName;
        }
    }

    /**
     * Adder methods are used to add elements to collections on a target bean. A typical use case is JPA. The
     * convention is that the element name will be equal to the remainder of the add method. Example: 'addElement'
     * element name will be 'element'.
     *
     * @param forType The concrete type this method is being inspected for
     * @param adderMethod getter or setter method.
     *
     * @return the property name.
     */
    @Override
    public String getBuilderElementName(TypeElement forType, ExecutableElement adderMethod, Elements elements,
                                        Types types) {
        String methodName = adderMethod.getSimpleName().toString();
        return decapitalize( methodName.substring( 3 ) );
    }

    @Override
    public BuilderNamingStrategy withBuilderProvider(BuilderProvider provider) {
        return new DefaultBuilderNamingStrategy( provider );
    }

    /**
     * Convenience method for looking up builder information.
     */
    protected BuilderInfo getBuilderMapping(TypeElement forType, ExecutableElement element, Elements elements,
                                            Types types) {
        if ( forType.getKind() == ElementKind.CLASS ) {
            return builderProvider.findBuildTarget( forType.asType(), elements, types );
        }
        return null;
    }
}
