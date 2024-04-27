/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import org.mapstruct.tools.gem.Gem;

/**
 * @author Filip Hrisafov
 */
public abstract class MetaAnnotations<G extends Gem, V> {

    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";

    private final ElementUtils elementUtils;
    private final String annotationFqn;

    protected MetaAnnotations(ElementUtils elementUtils, String annotationFqn) {
        this.elementUtils = elementUtils;
        this.annotationFqn = annotationFqn;
    }

    /**
     * Retrieves the processed annotations.
     *
     * @param source The source element of interest
     * @return The processed annotations for the given element
     */
    public Set<V> getProcessedAnnotations(Element source) {
        return getValues( source, source, new LinkedHashSet<>(), new HashSet<>() );
    }

    protected abstract G instanceOn(Element element);

    protected abstract void addInstance(G gem, Element source, Set<V> values);

    /**
     * Retrieves the processed annotations.
     *
     * @param source          The source element of interest
     * @param element         Element of interest: method, or (meta) annotation
     * @param values          the set of values found so far
     * @param handledElements The collection of already handled elements to handle recursion correctly.
     * @return The processed annotations for the given element
     */
    private Set<V> getValues(Element source, Element element, Set<V> values, Set<Element> handledElements) {
        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element annotationElement = annotationMirror.getAnnotationType().asElement();
            if ( isAnnotation( annotationElement, annotationFqn ) ) {
                G gem = instanceOn( element );
                addInstance( gem, source, values );
            }
            else if ( isNotJavaAnnotation( element ) && !handledElements.contains( annotationElement ) ) {
                handledElements.add( annotationElement );
                getValues( source, annotationElement, values, handledElements );
            }
        }
        return values;
    }

    private boolean isNotJavaAnnotation(Element element) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return !elementUtils.getPackageOf( element ).getQualifiedName().contentEquals( JAVA_LANG_ANNOTATION_PGK );
        }
        return true;
    }

    private boolean isAnnotation(Element element, String annotationFqn) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return ( (TypeElement) element ).getQualifiedName().contentEquals( annotationFqn );
        }

        return false;
    }
}
