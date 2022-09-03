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
import org.mapstruct.ap.internal.model.Mapper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into an application-scoped Jakarta CDI bean in case Jakarta CDI
 * is configured as the target component model for this mapper.
 *
 * @author Filip Hrisafov
 */
public class JakartaCdiComponentProcessor extends AnnotationBasedComponentModelProcessor {

    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.JAKARTA_CDI;
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return Collections.singletonList(
            new Annotation( getTypeFactory().getType( "jakarta.enterprise.context.ApplicationScoped" ) )
        );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Arrays.asList( new Annotation( getTypeFactory().getType( "jakarta.inject.Inject" ) ) );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return false;
    }

    @Override
    protected boolean additionalPublicEmptyConstructor() {
        return true;
    }

}
