/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement.AnnotationElementType;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a JSR 330 style bean in case "jsr330" is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class Jsr330ComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.JSR330;
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        if ( mapper.getDecorator() == null ) {
            return Arrays.asList( singleton(), named() );
        }
        else {
            return Arrays.asList( singleton(), namedDelegate( mapper ) );
        }
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations(Decorator decorator) {
        return Arrays.asList( singleton(), named() );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList( inject(), namedDelegate( mapper ) );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList( inject() );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation singleton() {
        return new Annotation( getType( "Singleton" ) );
    }

    private Annotation named() {
        return new Annotation( getType( "Named" ) );
    }

    private Annotation namedDelegate(Mapper mapper) {
        return new Annotation(
            getType( "Named" ),
            Collections.singletonList(
                new AnnotationElement(
                    AnnotationElementType.STRING,
                    Collections.singletonList( mapper.getPackageName() + "." + mapper.getName() )
                ) )
        );
    }

    private Annotation inject() {
        return new Annotation( getType( "Inject" ) );
    }

    private Type getType(String simpleName) {
        if ( getTypeFactory().isTypeAvailable( "javax.inject." + simpleName ) ) {
            return getTypeFactory().getType( "javax.inject." + simpleName );
        }

        if ( getTypeFactory().isTypeAvailable( "jakarta.inject." + simpleName ) ) {
            return getTypeFactory().getType( "jakarta.inject." + simpleName );
        }

        throw new AnnotationProcessingException(
            "Couldn't find any of the JSR330 or Jakarta Dependency Inject types." +
                " Are you missing a dependency on your classpath?" );
    }
}
