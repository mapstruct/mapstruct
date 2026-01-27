/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.regex.Pattern;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.util.IntrospectorUtils;

/**
 * The default JavaBeans-compliant implementation of the {@link AccessorNamingStrategy} service provider interface.
 *
 * @author Christian Schuster, Sjaak Derken
 */
public class DefaultAccessorNamingStrategy implements AccessorNamingStrategy {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    protected Elements elementUtils;
    protected Types typeUtils;

    @Override
    public void init(MapStructProcessingEnvironment processingEnvironment) {
        this.elementUtils = processingEnvironment.getElementUtils();
        this.typeUtils = processingEnvironment.getTypeUtils();
    }

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
     * Returns {@code true} when the {@link ExecutableElement} is a getter method. A method is a getter when it
     * has no parameters, starts
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
        if ( !method.getParameters().isEmpty() ) {
            // If the method has parameters it can't be a getter
            return false;
        }
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

        return methodName.startsWith( "set" ) && methodName.length() > 3 || isFluentSetter( method );
    }

    protected boolean isFluentSetter(ExecutableElement method) {
        return method.getParameters().size() == 1 &&
            !JAVA_JAVAX_PACKAGE.matcher( method.getEnclosingElement().asType().toString() ).matches() &&
            !isAdderWithUpperCase4thCharacter( method ) &&
            !isRemoverWithUpperCase7thCharacter( method ) &&
            typeUtils.isAssignable( method.getReturnType(), method.getEnclosingElement().asType() );
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
     * Checks that the method is a remover with an upper case 7th character. The reason for this is that methods such
     * as {@code removed(boolean removed)} are considered as setter and {@code removeName(String name)} too. We need to
     * make sure that {@code removeName} is considered as a remover and {@code removed} is considered as a setter.
     *
     * @param method the method that needs to be checked
     *
     * @return {@code true} if the method is a remover with an upper case 7th character, {@code false} otherwise
     */
    private boolean isRemoverWithUpperCase7thCharacter(ExecutableElement method) {
        return isRemoverMethod( method ) && Character.isUpperCase( method.getSimpleName().toString().charAt( 6 ) );
    }

    /**
     * Returns {@code true} when the {@link ExecutableElement} is a remover method. A remover method starts with
     * 'remove'. The remainder of the name is supposed to reflect the <em>singular</em> property name (as opposed to
     * plural) of its corresponding property. For example: property "children", but "removeChild". See also
     * {@link #getElementName(ExecutableElement) }.
     * <p>
     * The calling MapStruct code guarantees there's only one argument.
     * <p>
     *
     * @param method to be analyzed
     *
     * @return {@code true} when the method is a remover method.
     */
    public boolean isRemoverMethod(ExecutableElement method) {
        String methodName = method.getSimpleName().toString();

        return methodName.startsWith( "remove" ) && methodName.length() > 6;
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
        if ( isFluentSetter( getterOrSetterMethod ) ) {
            // If this is a fluent setter that starts with set and the 4th character is an uppercase one
            // then we treat it as a Java Bean style method (we get the property starting from the 4th character).
            // Otherwise we treat it as a fluent setter
            // For example, for the following methods:
            // * public Builder setSettlementDate(String settlementDate)
            // * public Builder settlementDate(String settlementDate)
            // We are going to extract the same property name settlementDate
            if ( methodName.startsWith( "set" )
                && methodName.length() > 3
                && Character.isUpperCase( methodName.charAt( 3 ) ) ) {
                return IntrospectorUtils.decapitalize( methodName.substring( 3 ) );
            }
            else {
                return methodName;
            }
        }
        return IntrospectorUtils.decapitalize( methodName.substring( methodName.startsWith( "is" ) ? 2 : 3 ) );
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
        return IntrospectorUtils.decapitalize( methodName.substring( 3 ) );
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
