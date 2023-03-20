/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

import java.util.Collections;
import java.util.List;

public class KoraComponentProcessor extends AnnotationBasedComponentModelProcessor {

    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.KORA;
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return Collections.singletonList(
                new Annotation( getTypeFactory().getType( "ru.tinkoff.kora.common.Component" )
                ) );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.emptyList();
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

}
