/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a OSGi DS Component in case "ds" is configured as the
 * target component model for this mapper.
 */
public class DsComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return "ds";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        if ( mapper.getDecorator() == null ) {
            return Arrays.asList( component() );
        }
        else {
            return Arrays.asList( component() );
        }
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations() {
        return Arrays.asList( component() );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList( reference() );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList( reference() );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation component() {
        return new Annotation( getTypeFactory().getType( "org.osgi.service.component.annotations.Component" ) );
    }

    private Annotation reference() {
        return new Annotation( getTypeFactory().getType( "org.osgi.service.component.annotations.Reference" ) );
    }
}
