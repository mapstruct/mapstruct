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
package org.mapstruct.ap.test.naming.spi;

import java.beans.Introspector;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

/**
 * A custom {@link AccessorNamingStrategy} recognizing getters in the form of {@code property()} and setters in the
 * form of {@code withProperty(value)}.
 *
 * @author Gunnar Morling
 */
public class CustomAccessorNamingStrategy extends DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public boolean isGetterMethod(ExecutableElement method) {
        return method.getReturnType().getKind() != TypeKind.VOID;
    }

    @Override
    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "with" ) && methodName.length() > 4;
    }

    @Override
    public boolean isAdderMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith( "add" ) && methodName.length() > 3;
    }

    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        return Introspector.decapitalize( methodName.startsWith( "with" ) ? methodName.substring(  4 ) : methodName );
    }

    @Override
    public String getElementName(ExecutableElement adderMethod) {
        String methodName = adderMethod.getSimpleName().toString();
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

}
