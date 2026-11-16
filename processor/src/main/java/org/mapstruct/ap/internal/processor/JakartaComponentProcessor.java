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

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Jakarta Inject style bean in case "jakarta" is configured as the
 * target component model for this mapper.
 *
 * @author Filip Hrisafov
 */
public class JakartaComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.JAKARTA;
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
        return new Annotation( getTypeFactory().getType( "jakarta.inject.Singleton" ) );
    }

    private Annotation named() {
        return new Annotation( getTypeFactory().getType( "jakarta.inject.Named" ) );
    }

    private Annotation namedDelegate(Mapper mapper) {
        return new Annotation(
            getTypeFactory().getType( "jakarta.inject.Named" ),
            Collections.singletonList(
                           new AnnotationElement( AnnotationElementType.STRING,
                               Collections.singletonList( mapper.getPackageName() + "." + mapper.getName() )
                           ) )
        );
    }

    private Annotation inject() {
        return new Annotation( getTypeFactory().getType( "jakarta.inject.Inject" ) );
    }

}
