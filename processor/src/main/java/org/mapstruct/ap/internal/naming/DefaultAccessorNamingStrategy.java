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
package org.mapstruct.ap.internal.naming;

import java.beans.Introspector;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;

import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.MethodType;

/**
 * The default JavaBeans-compliant implementation of the {@link AccessorNamingStrategy} service provider interface.
 *
 * @author Christian Schuster
 */
public class DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    @Override
    public MethodType getMethodType(ExecutableElement method) {
        if ( isGetterMethod( method ) ) {
            return MethodType.GETTER;
        }
        else if ( isSetterMethod( method ) ) {
            return MethodType.SETTER;
        }
        else if ( isAdderMethod( method ) ) {
            return MethodType.ADDER;
        }
        else {
            return MethodType.OTHER;
        }
    }

    private boolean isGetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        boolean isNonBooleanGetterName = methodName.startsWith( "get" ) && methodName.length() > 3 &&
            method.getReturnType().getKind() != TypeKind.VOID;

        boolean isBooleanGetterName = methodName.startsWith( "is" ) && methodName.length() > 2;
        boolean returnTypeIsBoolean = method.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean".equals( getQualifiedName( method.getReturnType() ) );

        return isNonBooleanGetterName || ( isBooleanGetterName && returnTypeIsBoolean );
    }

    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "set" ) && methodName.length() > 3;
    }

    public boolean isAdderMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "add" ) && methodName.length() > 3;
    }


    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        return Introspector.decapitalize( methodName.substring( methodName.startsWith( "is" ) ? 2 : 3 ) );
    }

    @Override
    public String getElementName(ExecutableElement adderMethod) {
        String methodName = adderMethod.getSimpleName().toString();
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

    @Override
    public String getCollectionGetterName(String property) {
        return "get" + property.substring( 0, 1 ).toUpperCase() + property.substring( 1 );
    }

    private static String getQualifiedName(TypeMirror type) {
        DeclaredType declaredType = type.accept(
            new SimpleTypeVisitor6<DeclaredType, Void>() {
                @Override
                public DeclaredType visitDeclared(DeclaredType t, Void p) {
                    return t;
                }
            },
            null
        );

        if ( declaredType == null ) {
            return null;
        }

        TypeElement typeElement = declaredType.asElement().accept(
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            },
            null
        );

        return typeElement != null ? typeElement.getQualifiedName().toString() : null;
    }

}
