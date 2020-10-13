/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static javax.lang.model.util.ElementFilter.fieldsIn;
import static org.mapstruct.ap.internal.util.SpecificCompilerWorkarounds.replaceTypeElementIfNecessary;

/**
 * Provides functionality around {@link VariableElement}s.
 *
 * @author Sjaak Derksen
 */
public class Fields {

    private Fields() {
    }

    public static boolean isFieldAccessor(VariableElement method) {
        return isPublic( method ) && isNotStatic( method );
    }

    static boolean isPublic(VariableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isNotStatic(VariableElement method) {
        return !method.getModifiers().contains( Modifier.STATIC );
    }

    /**
     * Finds all variable elements within the given type element, including variable
     * elements defined in super classes and implemented interfaces and including the fields in the .
     *
     * @param elementUtils element helper
     * @param element the element to inspect
     *
     * @return the executable elements usable in the type
     */
    public static List<VariableElement> getAllEnclosedFields(ElementUtils elementUtils, TypeElement element) {
        List<VariableElement> enclosedElements = new ArrayList<>();
        element = replaceTypeElementIfNecessary( elementUtils, element );
        addEnclosedElementsInHierarchy( elementUtils, enclosedElements, element, element );

        return enclosedElements;
    }

    private static void addEnclosedElementsInHierarchy(ElementUtils elementUtils, List<VariableElement> alreadyAdded,
                                                       TypeElement element, TypeElement parentType) {
        if ( element != parentType ) { // otherwise the element was already checked for replacement
            element = replaceTypeElementIfNecessary( elementUtils, element );
        }

        if ( element.asType().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        addFields( alreadyAdded, fieldsIn( element.getEnclosedElements() ) );


        if ( hasNonObjectSuperclass( element ) ) {
            addEnclosedElementsInHierarchy(
                            elementUtils,
                            alreadyAdded,
                            asTypeElement( element.getSuperclass() ),
                            parentType
            );
        }
    }

    private static void addFields(List<VariableElement> alreadyCollected, List<VariableElement> variablesToAdd) {
        List<VariableElement> safeToAdd = new ArrayList<>( variablesToAdd.size() );
        safeToAdd.addAll( variablesToAdd );

        alreadyCollected.addAll( 0, safeToAdd );
    }

    private static TypeElement asTypeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
    }

    private static boolean hasNonObjectSuperclass(TypeElement element) {
        if ( element.getSuperclass().getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( element );
        }

        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && !asTypeElement( element.getSuperclass() ).getQualifiedName().toString().equals( "java.lang.Object" );
    }
}
