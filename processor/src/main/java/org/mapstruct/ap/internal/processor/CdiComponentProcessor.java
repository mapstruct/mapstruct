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
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into an application-scoped CDI bean in case CDI is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 */
public class CdiComponentProcessor extends AnnotationBasedComponentModelProcessor {

    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.CDI;
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return Collections.singletonList(
                new Annotation( getType( "ApplicationScoped" ) )
        );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Arrays.asList( new Annotation( getType( "Inject" ) ) );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return false;
    }

    @Override
    protected boolean additionalPublicEmptyConstructor() {
        return true;
    }

    private Type getType(String simpleName) {
        String javaxPrefix = "javax.inject.";
        String jakartaPrefix = "jakarta.inject.";
        if ( "ApplicationScoped".equals( simpleName ) ) {
            javaxPrefix = "javax.enterprise.context.";
            jakartaPrefix = "jakarta.enterprise.context.";
        }
        if ( getTypeFactory().isTypeAvailable( javaxPrefix + simpleName ) ) {
            return getTypeFactory().getType( javaxPrefix + simpleName );
        }

        if ( getTypeFactory().isTypeAvailable( jakartaPrefix + simpleName ) ) {
            return getTypeFactory().getType( jakartaPrefix + simpleName );
        }

        throw new AnnotationProcessingException(
            "Couldn't find any of the CDI or Jakarta CDI Dependency types." +
                " Are you missing a dependency on your classpath?" );
    }
}
