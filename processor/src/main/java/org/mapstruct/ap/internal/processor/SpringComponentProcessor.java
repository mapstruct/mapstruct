/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement.AnnotationElementType;

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
        return isAlreadyAnnotatedAsSpringComponent( mapper )
            || isAlreadyAnnotatedAsSpringService( mapper );
    }

    private boolean isAlreadyAnnotatedAsSpringComponent(Mapper mapper) {
        return this.isAlreadyAnnotatedAs( mapper, "org.springframework.stereotype.Component" );
    }

    private boolean isAlreadyAnnotatedAsSpringService(Mapper mapper) {
        return this.isAlreadyAnnotatedAs( mapper, "org.springframework.stereotype.Service" );
    }

    private boolean isAlreadyAnnotatedAs(Mapper mapper, String annotationFullyQualifiedName) {
        return mapper.getAnnotations()
                .stream()
                .anyMatch(
                        annotation -> annotation
                                .getType()
                                .getFullyQualifiedName()
                                .equals( annotationFullyQualifiedName ) );
    }
}
