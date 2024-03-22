/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import androidx.room.compiler.processing.XProcessingEnv;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class EclipseElementUtilsDecorator extends AbstractElementUtilsDecorator {

    private final XProcessingEnv delegate;

    EclipseElementUtilsDecorator(XProcessingEnv processingEnv, TypeElement mapperElement) {
        super( processingEnv, mapperElement );
        this.delegate = processingEnv;
    }

    /**
     * When running during Eclipse Incremental Compilation, we might get a TypeElement that has an UnresolvedTypeBinding
     * and which is not automatically resolved. In that case, getEnclosedElements returns an empty list. We take that as
     * a hint to check if the TypeElement resolved by FQN might have any enclosed elements and, if so, return the
     * resolved element.
     *
     * @param element the original element
     * @return the element freshly resolved using the qualified name, if the original element did not return any
     *         enclosed elements, whereas the resolved element does return enclosed elements.
     */
    protected TypeElement replaceTypeElementIfNecessary(TypeElement element) {
        if ( element.getEnclosedElements().isEmpty() ) {
            TypeElement resolvedByName = delegate.getTypeElement( element.getQualifiedName() );
            if ( resolvedByName != null && !resolvedByName.getEnclosedElements().isEmpty() ) {
                return resolvedByName;
            }
        }
        return element;
    }
}
