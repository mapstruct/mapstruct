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

    protected abstract SINGULAR singularInstanceOn(Element element);

    protected abstract MULTIPLE multipleInstanceOn(Element element);

    protected abstract void addInstance(SINGULAR gem, Element method, Set<OPTIONS> mappings);

    protected abstract void addInstances(MULTIPLE gem, Element method, Set<OPTIONS> mappings);

    /**
     * Retrieves the mappings configured via {@code @Mapping} from the given method.
     *
     * @param method The method of interest
     * @param beanMapping options coming from bean mapping method
     * @return The mappings for the given method, keyed by target property name
     */
    public Set<OPTIONS> getMappings(Element method) {
        return getMappings( method, method, new LinkedHashSet<>(), new HashSet<>() );
    }

    /**
     * Retrieves the mappings configured via {@code @Mapping} from the given method.
     *
     * @param method The method of interest
     * @param element Element of interest: method, or (meta) annotation
     * @param beanMapping options coming from bean mapping method
     * @param mappingOptions LinkedSet of mappings found so far
     * @return The mappings for the given method, keyed by target property name
     */
    private Set<OPTIONS> getMappings(Element method, Element element,
                                     LinkedHashSet<OPTIONS> mappingOptions,
                                              Set<Element> handledElements) {

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element lElement = annotationMirror.getAnnotationType().asElement();
            if ( isAnnotation( lElement, singularFqn ) ) {
                // although getInstanceOn does a search on annotation mirrors, the order is preserved
                SINGULAR mapping = singularInstanceOn( element );
                addInstance( mapping, method, mappingOptions );
            }
            else if ( isAnnotation( lElement, multipleFqn ) ) {
                // although getInstanceOn does a search on annotation mirrors, the order is preserved
                MULTIPLE mappings = multipleInstanceOn( element );
                addInstances( mappings, method, mappingOptions );
            }
            else if ( !isAnnotationInPackage( lElement, JAVA_LANG_ANNOTATION_PGK )
                && !isAnnotationInPackage( lElement, ORG_MAPSTRUCT_PKG )
                && !handledElements.contains( lElement ) ) {
                // recur over annotation mirrors
                handledElements.add( lElement );
                getMappings( method, lElement, mappingOptions, handledElements );
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