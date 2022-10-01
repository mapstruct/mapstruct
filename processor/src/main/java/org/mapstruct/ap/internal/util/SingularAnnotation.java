/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import org.mapstruct.tools.gem.Gem;

/**
 * @author orange add
 */
public abstract class SingularAnnotation<SINGULAR extends Gem, OPTIONS> {
    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";
    private ElementUtils elementUtils;
    private final String singularFqn;

    protected SingularAnnotation(ElementUtils elementUtils, String singularFqn) {
        this.elementUtils = elementUtils;
        this.singularFqn = singularFqn;
    }

    /**
     * @param element the element on which the Gem needs to be found
     * @return the Gem found on the element.
     */
    protected abstract SINGULAR singularInstanceOn(Element element);

    /**
     * @param gem the annotation gem to be processed
     * @param source the source element where the request originated from
     */
    protected abstract OPTIONS newInstance(SINGULAR gem, Element source);

    /**
     * Retrieves the processed annotation.
     *
     * @param source The source element of interest
     * @return The processed annotation for the given element
     */
    public OPTIONS getProcessedAnnotation(Element source) {
        return getMapping( source, source, new HashSet<>() );
    }

    /**
     * Retrieves the processed annotation.
     *
     * @param source The source element of interest
     * @param element Element of interest: method, or (meta) annotation
     * @param handledElements The collection of already handled elements to handle recursion correctly.
     * @return The processed annotation for the given element
     */
    private OPTIONS getMapping(Element source, Element element,
                                              Set<Element> handledElements) {

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element lElement = annotationMirror.getAnnotationType().asElement();
            if ( isAnnotation( lElement, singularFqn ) ) {
                // although getInstanceOn does a search on annotation mirrors, the order is preserved
                SINGULAR mapping = singularInstanceOn( element );
                return newInstance( mapping, source );
            }
            else if ( !isAnnotationInPackage( lElement, JAVA_LANG_ANNOTATION_PGK )
                && !isAnnotationInPackage( lElement, ORG_MAPSTRUCT_PKG )
                && !handledElements.contains( lElement ) ) {
                // recur over annotation mirrors
                handledElements.add( lElement );
                OPTIONS options = getMapping( source, lElement, handledElements );
                if ( options != null ) {
                    return options;
                }
            }
        }
        return null;
    }

    private boolean isAnnotationInPackage(Element element, String packageFQN) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return packageFQN.equals( elementUtils.getPackageOf( element ).getQualifiedName().toString() );
        }
        return false;
    }

    private boolean isAnnotation(Element element, String annotationFQN) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return annotationFQN.equals( ( (TypeElement) element ).getQualifiedName().toString() );
        }
        return false;
    }
}
