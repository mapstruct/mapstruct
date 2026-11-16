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
 * A base helper class that provides utility methods for working with repeatable annotations.
 *
 * @param <SINGULAR> The singular annotation type
 * @param <MULTIPLE> The multiple annotation type
 * @param <OPTIONS> The underlying holder for the processed annotations
 *
 * @author Ben Zegveld
 */
public abstract class RepeatableAnnotations<SINGULAR extends Gem, MULTIPLE extends Gem, OPTIONS> {
    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";

    private ElementUtils elementUtils;
    private final String singularFqn;
    private final String multipleFqn;

    protected RepeatableAnnotations(ElementUtils elementUtils, String singularFqn, String multipleFqn) {
        this.elementUtils = elementUtils;
        this.singularFqn = singularFqn;
        this.multipleFqn = multipleFqn;
    }

    /**
     * @param element the element on which the Gem needs to be found
     * @return the Gem found on the element.
     */
    protected abstract SINGULAR singularInstanceOn(Element element);

    /**
     * @param element the element on which the Gems needs to be found
     * @return the Gems found on the element.
     */
    protected abstract MULTIPLE multipleInstanceOn(Element element);

    /**
     * @param gem the annotation gem to be processed
     * @param source the source element where the request originated from
     * @param mappings the collection of completed processing
     */
    protected abstract void addInstance(SINGULAR gem, Element source, Set<OPTIONS> mappings);

    /**
     * @param gems the annotation gems to be processed
     * @param source the source element where the request originated from
     * @param mappings the collection of completed processing
     */
    protected abstract void addInstances(MULTIPLE gems, Element source, Set<OPTIONS> mappings);

    /**
     * Retrieves the processed annotations.
     *
     * @param source The source element of interest
     * @return The processed annotations for the given element
     */
    public Set<OPTIONS> getProcessedAnnotations(Element source) {
        return getMappings( source, source, new LinkedHashSet<>(), new HashSet<>() );
    }

    /**
     * Retrieves the processed annotations.
     *
     * @param source The source element of interest
     * @param element Element of interest: method, or (meta) annotation
     * @param mappingOptions LinkedSet of mappings found so far
     * @param handledElements The collection of already handled elements to handle recursion correctly.
     * @return The processed annotations for the given element
     */
    private Set<OPTIONS> getMappings(Element source, Element element,
                                     LinkedHashSet<OPTIONS> mappingOptions,
                                              Set<Element> handledElements) {

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element lElement = annotationMirror.getAnnotationType().asElement();
            if ( isAnnotation( lElement, singularFqn ) ) {
                // although getInstanceOn does a search on annotation mirrors, the order is preserved
                SINGULAR mapping = singularInstanceOn( element );
                addInstance( mapping, source, mappingOptions );
            }
            else if ( isAnnotation( lElement, multipleFqn ) ) {
                // although getInstanceOn does a search on annotation mirrors, the order is preserved
                MULTIPLE mappings = multipleInstanceOn( element );
                addInstances( mappings, source, mappingOptions );
            }
            else if ( !isAnnotationInPackage( lElement, JAVA_LANG_ANNOTATION_PGK )
                && !isAnnotationInPackage( lElement, ORG_MAPSTRUCT_PKG )
                && !handledElements.contains( lElement ) ) {
                // recur over annotation mirrors
                handledElements.add( lElement );
                getMappings( source, lElement, mappingOptions, handledElements );
            }
        }
        return mappingOptions;
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
