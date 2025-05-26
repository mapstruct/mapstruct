/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement.AnnotationElementType;

import static javax.lang.model.element.ElementKind.PACKAGE;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class SpringComponentProcessor extends AnnotationBasedComponentModelProcessor {

    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.SPRING;
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        List<Annotation> typeAnnotations = new ArrayList<>();
        if ( !isAlreadyAnnotatedAsSpringStereotype( mapper ) ) {
            typeAnnotations.add( component() );
        }

        if ( mapper.getDecorator() != null ) {
            typeAnnotations.add( qualifierDelegate() );
        }

        return typeAnnotations;
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations() {
        return Arrays.asList(
            component(),
            primary()
        );
    }

    /**
     * Returns the annotations that need to be added to the generated decorator, filtering out any annotations
     * that are already present or represented as meta-annotations.
     *
     * @param decorator the decorator to process
     * @return A list of annotations that should be added to the generated decorator.
     */
    @Override
    protected List<Annotation> getDecoratorAnnotations(Decorator decorator) {
        List<Annotation> desiredAnnotations = getDecoratorAnnotations();
        if ( desiredAnnotations.isEmpty() ) {
            return Collections.emptyList();
        }

        List<Annotation> result = new ArrayList<>( desiredAnnotations.size() );
        for ( Annotation annotation : desiredAnnotations ) {
            boolean alreadyPresent = false;

            // Check if the annotation or its meta-annotation is already present
            for ( Annotation existingAnnotation : decorator.getAnnotations() ) {
                Set<Element> handledElements = new HashSet<>();
                Element annotationElement = existingAnnotation.getType().getTypeElement();
                if ( hasAnnotationOrMetaAnnotation( annotationElement, annotation, handledElements ) ) {
                    alreadyPresent = true;
                    break;
                }
            }

            if ( !alreadyPresent ) {
                result.add( annotation );
            }
        }

        return result;
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList(
            autowired()
        );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList(
            autowired(),
            qualifierDelegate()
        );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation autowired() {
        return new Annotation( getTypeFactory().getType( "org.springframework.beans.factory.annotation.Autowired" ) );
    }

    private Annotation qualifierDelegate() {
        return new Annotation(
            getTypeFactory().getType( "org.springframework.beans.factory.annotation.Qualifier" ),
            Collections.singletonList(
                           new AnnotationElement(
                               AnnotationElementType.STRING,
                               Collections.singletonList( "delegate" )
                           ) ) );
    }

    private Annotation primary() {
        return new Annotation( getTypeFactory().getType( "org.springframework.context.annotation.Primary" ) );
    }

    private Annotation component() {
        return new Annotation( getTypeFactory().getType( "org.springframework.stereotype.Component" ) );
    }

    private boolean isAlreadyAnnotatedAsSpringStereotype(Mapper mapper) {
        Set<Element> handledElements = new HashSet<>();
        Annotation componentAnnotation = component();

        return mapper.getAnnotations()
            .stream()
            .anyMatch(
                annotation -> hasAnnotationOrMetaAnnotation(
                    annotation.getType().getTypeElement(),
                    componentAnnotation,
                    handledElements
                )
            );
    }

    /**
     * Checks if an element has a specific annotation or meta-annotation.
     *
     * @param element         the element to check
     * @param annotation      the annotation to look for
     * @param handledElements set of already handled elements to avoid infinite recursion
     * @return true if the element has the annotation or a meta-annotation
     */
    protected boolean hasAnnotationOrMetaAnnotation(Element element, Annotation annotation,
                                                    Set<Element> handledElements) {
        if ( element instanceof TypeElement &&
            annotation.getType().getTypeElement().getQualifiedName().toString().equals(
                ( (TypeElement) element ).getQualifiedName().toString() ) ) {
            return true;
        }

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element annotationElement = annotationMirror.getAnnotationType().asElement();
            // Bypass java lang annotations to improve performance avoiding unnecessary checks
            if ( !isAnnotationInPackage( annotationElement, "java.lang.annotation" ) &&
                !handledElements.contains( annotationElement ) ) {
                handledElements.add( annotationElement );
                if ( annotation.getType().getTypeElement().getQualifiedName().toString().equals(
                    ( (TypeElement) annotationElement ).getQualifiedName().toString() ) ||
                    hasAnnotationOrMetaAnnotation( annotationElement, annotation, handledElements ) ) {
                    return true;
                }
            }
        }

        return false;
    }

    private PackageElement getPackageOf(Element element) {
        while ( element.getKind() != PACKAGE ) {
            element = element.getEnclosingElement();
        }

        return (PackageElement) element;
    }

    private boolean isAnnotationInPackage(Element element, String packageFQN) {
        return packageFQN.equals( getPackageOf( element ).getQualifiedName().toString() );
    }
}
