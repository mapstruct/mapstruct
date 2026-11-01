/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.contextlost;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Reproduces the {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} bug where
 * {@code @Context} parameters of the user method are lost in the generated helper.
 * <p>
 * The helper maps {@code ContextLostInput} through the user method {@code toInt} into the
 * intermediate type {@code Integer}, from which the built-in {@code Integer -> String}
 * conversion produces the target type. The helper body calls
 * {@code toInt( a, log )}, but {@code log} — the {@code @Context} parameter of {@code toInt} —
 * is neither passed to the helper nor declared as its parameter, so the generated code does
 * not compile: <i>cannot find symbol variable log</i>.
 */
@Mapper
public interface ContextLostMapper {

    ContextLostMapper INSTANCE = Mappers.getMapper( ContextLostMapper.class );

    @Nullable
    default Integer toInt( @Nullable ContextLostInput a, @Context StringBuilder log ) {
        if ( a == null ) {
            return null;
        }
        log.append( "t" );
        return 42;
    }

    @Mapping( target = "number", source = "input" )
    ContextLostTargetBean map( ContextLostSourceBean h, @Context StringBuilder log );
}
