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
package org.mapstruct.ap.services;

import java.beans.Introspector;

import org.mapstruct.spi.AccessorNamingStrategy;

/**
 * The default JavaBeans-compliant implementation of the {@link AccessorNamingStrategy} service provider interface.
 *
 * @author Christian Schuster
 */
class DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public boolean isNonBooleanGetterName(String methodName) {
        return methodName.startsWith( "get" ) && methodName.length() > 3;
    }

    @Override
    public boolean isBooleanGetterName(String methodName) {
        return methodName.startsWith( "is" ) && methodName.length() > 2;
    }

    @Override
    public boolean isSetterName(String methodName) {
        return methodName.startsWith( "set" ) && methodName.length() > 3;
    }

    @Override
    public boolean isAdderName(String methodName) {
        return methodName.startsWith( "add" ) && methodName.length() > 3;
    }

    @Override
    public String getPropertyNameForNonBooleanGetterName(String methodName) {
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

    @Override
    public String getPropertyNameForBooleanGetterName(String methodName) {
        return Introspector.decapitalize( methodName.substring( 2 ) );
    }

    @Override
    public String getPropertyNameForSetterName(String methodName) {
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

    @Override
    public String getElementNameForAdderName(String methodName) {
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

    @Override
    public String getNonBooleanGetterNameForSetterName(String methodName) {
        return "get" + methodName.substring( 3 );
    }
}
