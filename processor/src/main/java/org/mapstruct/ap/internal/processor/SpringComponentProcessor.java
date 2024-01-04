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

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement.AnnotationElementType;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

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
        return mapper.getAnnotations()
            .stream()
            .anyMatch(
                annotation -> isOrIncludesComponentAnnotation( annotation, handledElements )
            );
    }

    private boolean isOrIncludesComponentAnnotation(Annotation annotation, Set<Element> handledElements) {
        return isOrIncludesComponentAnnotation(
            annotation.getType().getTypeElement(), handledElements
        );
    }

    private boolean isOrIncludesComponentAnnotation(Element element, Set<Element> handledElements) {
        if ( "org.springframework.stereotype.Component".equals(
                ( (TypeElement) element ).getQualifiedName().toString()
        )) {
            return true;
        }

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element annotationMirrorElement = annotationMirror.getAnnotationType().asElement();
            // Bypass java lang annotations to improve performance avoiding unnecessary checks
            if ( !isAnnotationInPackage( annotationMirrorElement, "java.lang.annotation" ) &&
                 !handledElements.contains( annotationMirrorElement ) ) {
                handledElements.add( annotationMirrorElement );
                boolean isOrIncludesComponentAnnotation = isOrIncludesComponentAnnotation(
                    annotationMirrorElement, handledElements
                );

                if ( isOrIncludesComponentAnnotation ) {
                    return true;
                }
            }
        }

        return false;
    }

    private PackageElement getPackageOf( Element element ) {
        while ( element.getKind() != PACKAGE ) {
            element = element.getEnclosingElement();
        }

        return (PackageElement) element;
    }

    private boolean isAnnotationInPackage(Element element, String packageFQN) {
        return packageFQN.equals( getPackageOf( element ).getQualifiedName().toString() );
    }
}
