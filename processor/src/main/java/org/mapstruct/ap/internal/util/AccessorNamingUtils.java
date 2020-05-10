/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;

import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.spi.AccessorNamingStrategy;
import org.mapstruct.ap.spi.MethodType;

import static org.mapstruct.ap.internal.util.Executables.isPublicNotStatic;

/**
 * Utils for working with the {@link AccessorNamingStrategy}.
 *
 * @author Filip Hrisafov
 */
public final class AccessorNamingUtils {

    private final AccessorNamingStrategy accessorNamingStrategy;

    public AccessorNamingUtils(AccessorNamingStrategy accessorNamingStrategy) {
        this.accessorNamingStrategy = accessorNamingStrategy;
    }

    public boolean isGetterMethod(ExecutableElement executable) {
        return executable != null && isPublicNotStatic( executable ) &&
            executable.getParameters().isEmpty() &&
            accessorNamingStrategy.getMethodType( executable ) == MethodType.GETTER;
    }

    public boolean isPresenceCheckMethod(ExecutableElement executable) {

        return executable != null
            && isPublicNotStatic( executable )
            && executable.getParameters().isEmpty()
            && ( executable.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean".equals( getQualifiedName( executable.getReturnType() ) ) )
            && accessorNamingStrategy.getMethodType( executable ) == MethodType.PRESENCE_CHECKER;
    }

    public ExecutableElement findPresenceCheckMethodFor(ExecutableElement executable) {
        if ( isGetterMethod( executable ) && executable.getReturnType().getKind() == TypeKind.DECLARED ) {
            DeclaredType returnType = (DeclaredType) executable.getReturnType();
            // look for nested custom presence checker in property enclosed methods
            String typeName = typeFullName( returnType );
            String customPresenceChecker = accessorNamingStrategy.getPresenceCheckerMethodInType( typeName );
            if ( customPresenceChecker != null ) {
                for ( Element element : returnType.asElement().getEnclosedElements() ) {
                    if ( element instanceof ExecutableElement &&
                        element.getSimpleName().contentEquals( customPresenceChecker ) &&
                        isBooleanPublicMethod( (ExecutableElement) element ) ) {
                        return (ExecutableElement) element;
                    }
                }
            }

        }
        return null;
    }

    private String typeFullName(DeclaredType type) {
        // FIXME: how to get type package name
        Object packageName = type.asElement().getEnclosingElement().toString().replace( "package ", "" );
        return packageName + "." + type.asElement().getSimpleName().toString();
    }

    private boolean isBooleanPublicMethod(ExecutableElement method) {
        return isPublicNotStatic( method )
            && method.getParameters().isEmpty()
            && ( method.getReturnType().getKind() == TypeKind.BOOLEAN ||
            "java.lang.Boolean"
                .equals( getQualifiedName( method.getReturnType() ) ) );
    }

    public boolean isSetterMethod(ExecutableElement executable) {
        return executable != null
            && isPublicNotStatic( executable )
            && executable.getParameters().size() == 1
            && accessorNamingStrategy.getMethodType( executable ) == MethodType.SETTER;
    }

    public boolean isAdderMethod(ExecutableElement executable) {
        return executable != null
            && isPublicNotStatic( executable )
            && executable.getParameters().size() == 1
            && accessorNamingStrategy.getMethodType( executable ) == MethodType.ADDER;
    }

    public String getPropertyName(ExecutableElement executable) {
        return accessorNamingStrategy.getPropertyName( executable );
    }

    /**
     * @param adderMethod the adder method
     *
     * @return the 'element name' to which an adder method applies. If. e.g. an adder method is named
     * {@code addChild(Child v)}, the element name would be 'Child'.
     */
    public String getElementNameForAdder(Accessor adderMethod) {
        if ( adderMethod.getAccessorType() == AccessorType.ADDER ) {
            return accessorNamingStrategy.getElementName( (ExecutableElement) adderMethod.getElement() );
        }
        else {
            return null;
        }
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
