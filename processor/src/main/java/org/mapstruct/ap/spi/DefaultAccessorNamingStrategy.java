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

import java.beans.Introspector;
import java.util.regex.Pattern;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;


/**
 * The default JavaBeans-compliant implementation of the {@link AccessorNamingStrategy} service provider interface.
 *
 * @author Christian Schuster, Sjaak Derken
 */
public class DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

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
        else if ( isPresenceCheckMethod( method ) ) {
            return MethodType.PRESENCE_CHECKER;
        }
        else {
            return MethodType.OTHER;
        }
    }

    /**
     * Returns {@code true} when the {@link ExecutableElement} is a getter method. A method is a getter when it starts
     * with 'get' and the return type is any type other than {@code void}, OR the getter starts with 'is' and the type
     * returned is a primitive or the wrapper for {@code boolean}. NOTE: the latter does strictly not comply to the bean
     * convention. The remainder of the name is supposed to reflect the property name.
     * <p>
     * The calling MapStruct code guarantees that the given method has no arguments.
     *
     * @param method to be analyzed
     *
     * @return {@code true} when the method is a getter.
     */
    public boolean isGetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        boolean isNonBooleanGetterName = methodName.startsWith( "get" ) && methodName.length() > 3 &&
            method.getReturnType().getKind() != TypeKind.VOID;

        boolean isBooleanGetterName = methodName.startsWith( "is" ) && methodName.length() > 2;
        boolean returnTypeIsBoolean = method.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean".equals( getQualifiedName( method.getReturnType() ) );

        return isNonBooleanGetterName || ( isBooleanGetterName && returnTypeIsBoolean );
    }


    /**
     * Returns {@code true} when the {@link ExecutableElement} is a setter method. A setter starts with 'set'. The
     * remainder of the name is supposed to reflect the property name.
     * <p>
     * The calling MapStruct code guarantees that there's only one argument.
     *
     * @param method to be analyzed
     * @return {@code true} when the method is a setter.
     */
    public boolean isSetterMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "set" ) && methodName.length() > 3 || isBuilderSetter( method );
    }

    protected boolean isBuilderSetter(ExecutableElement method) {
        return method.getParameters().size() == 1 &&
            !JAVA_JAVAX_PACKAGE.matcher( method.getEnclosingElement().asType().toString() ).matches() &&
            !isAdderWithUpperCase4thCharacter( method ) &&
            //TODO The Types need to be compared with Types#isSameType(TypeMirror, TypeMirror)
            method.getReturnType().toString().equals( method.getEnclosingElement().asType().toString() );
    }

    /**
     * Checks that the method is an adder with an upper case 4th character. The reason for this is that methods such
     * as {@code address(String address)} are considered as setter and {@code addName(String name)} too. We need to
     * make sure that {@code addName} is considered as an adder and {@code address} is considered as a setter.
     *
     * @param method the method that needs to be checked
     *
     * @return {@code true} if the method is an adder with an upper case 4h character, {@code false} otherwise
     */
    private boolean isAdderWithUpperCase4thCharacter(ExecutableElement method) {
        return isAdderMethod( method ) && Character.isUpperCase( method.getSimpleName().toString().charAt( 3 ) );
    }

    /**
     * Returns {@code true} when the {@link ExecutableElement} is an adder method. An adder method starts with 'add'.
     * The remainder of the name is supposed to reflect the <em>singular</em> property name (as opposed to plural) of
     * its corresponding property. For example: property "children", but "addChild". See also
     * {@link #getElementName(ExecutableElement) }.
     * <p>
     * The calling MapStruct code guarantees there's only one argument.
     * <p>
     *
     * @param method to be analyzed
     *
     * @return {@code true} when the method is an adder method.
     */
    public boolean isAdderMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "add" ) && methodName.length() > 3;
    }

    /**
     * Returns {@code true} when the {@link ExecutableElement} is a <em>presence check</em> method that checks if the
     * corresponding property is present (e.g. not null, not nil, ..). A presence check method  method starts with
     * 'has'. The remainder of the name is supposed to reflect the property name.
     * <p>
     * The calling MapStruct code guarantees there's no argument and that the return type is boolean or a
     * {@link Boolean}
     *
     * @param method to be analyzed
     * @return {@code true} when the method is a presence check method.
     */
    public boolean isPresenceCheckMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();
        return methodName.startsWith( "has" ) && methodName.length() > 3;
    }

    /**
     * Analyzes the method (getter or setter) and derives the property name.
     * See {@link #isGetterMethod(ExecutableElement)} {@link #isSetterMethod(ExecutableElement)}. The first three
     * ('get' / 'set' scenario) characters are removed from the simple name, or the first 2 characters ('is' scenario).
     * From the remainder the first character is made into small case (to counter camel casing) and the result forms
     * the property name.
     *
     * @param getterOrSetterMethod getter or setter method.
     *
     * @return the property name.
     */
    @Override
    public String getPropertyName(ExecutableElement getterOrSetterMethod) {
        String methodName = getterOrSetterMethod.getSimpleName().toString();
        if ( methodName.startsWith( "is" ) || methodName.startsWith( "get" ) || methodName.startsWith( "set" ) ) {
            return Introspector.decapitalize( methodName.substring( methodName.startsWith( "is" ) ? 2 : 3 ) );
        }
        else if ( isBuilderSetter( getterOrSetterMethod ) ) {
            return methodName;
        }
        return Introspector.decapitalize( methodName.substring( methodName.startsWith( "is" ) ? 2 : 3 ) );
    }

    /**
     * Adder methods are used to add elements to collections on a target bean. A typical use case is JPA. The
     * convention is that the element name will be equal to the remainder of the add method. Example: 'addElement'
     * element name will be 'element'.
     *
     * @param adderMethod getter or setter method.
     *
     * @return the property name.
     */
    @Override
    public String getElementName(ExecutableElement adderMethod) {
        String methodName = adderMethod.getSimpleName().toString();
        return Introspector.decapitalize( methodName.substring( 3 ) );
    }

   /**
     * Helper method, to obtain the fully qualified name of a type.
     *
     * @param type input type
     *
     * @return fully qualified name of type when the type is a {@link DeclaredType}, null when otherwise.
     */
    protected static String getQualifiedName(TypeMirror type) {
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

    @Override
    public String getCollectionGetterName(String property) {
        throw new IllegalStateException( "This method is not intended to be called anymore and will be removed in "
            + "future versions." );
    }

}
