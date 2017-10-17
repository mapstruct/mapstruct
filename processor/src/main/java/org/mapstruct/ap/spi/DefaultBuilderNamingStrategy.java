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

import static java.beans.Introspector.decapitalize;

import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.spi.BuilderNamingStrategy;
import org.mapstruct.ap.spi.MethodType;

/**
 * The default implementation of {@link BuilderNamingStrategy} that assumes the builder:
 *
 * <ul>
 *     <li>Won't have any getters</li>
 *     <li>Setters will be prefixed with either "set" or "with"</li>
 *     <li>Adder methods are prefixed with "add"</li>
 * </ul>
 *
 * @author Eric Martineau
 */
public class DefaultBuilderNamingStrategy implements BuilderNamingStrategy {
    private static final String ADDER_METHOD_PREFIX = "add";
    private static final String SET_METHOD_PREFIX = "set";
    private static final String WITH_METHOD_PREFIX = "with";

    @Override
    public MethodType getMethodType(ExecutableElement method) {
        if ( method.getParameters().isEmpty() ) {
            return MethodType.OTHER;
        }
        else if ( method.getSimpleName().toString().startsWith( ADDER_METHOD_PREFIX ) ) {
            return MethodType.ADDER;
        }
        else {
            return MethodType.SETTER;
        }
    }

    @Override
    public String getPropertyName(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        if ( methodName.startsWith( WITH_METHOD_PREFIX ) ) {
            return decapitalize( methodName.substring( 4 ) );
        }
        else if ( methodName.startsWith( SET_METHOD_PREFIX ) ) {
            return decapitalize( methodName.substring( 3 ) );
        }
        else {
            return methodName;
        }
    }

    @Override
    public String getElementName(ExecutableElement adderMethod) {
        String methodName = adderMethod.getSimpleName().toString();
        return decapitalize( methodName.substring( 3 ) );
    }

    @Override
    @Deprecated
    public String getCollectionGetterName(String property) {
        return null;
    }
}
