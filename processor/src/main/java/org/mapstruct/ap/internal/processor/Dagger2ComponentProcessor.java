/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.common.Accessibility;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Dagger2 style bean in case "dagger2" is configured as the
 * target component model for this mapper.
 *
 * @author Leonardo Lima
 */
public class Dagger2ComponentProcessor extends Jsr330ComponentProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return MappingConstantsGem.ComponentModelGem.DAGGER2;
    }

    @Override
    protected boolean requiresGenerationOfConstructor() {
        return true;
    }

    @Override
    protected Accessibility getMapperReferenceAccessibility(InjectionStrategyGem injectionStrategy) {
        return injectionStrategy == InjectionStrategyGem.CONSTRUCTOR ? Accessibility.PRIVATE : Accessibility.DEFAULT;
    }
}
