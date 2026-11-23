/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Reproduces the {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} local
 * variable clash in the "method then built-in conversion" shape of
 * {@code org.mapstruct.ap.internal.processor.creation.MappingResolverImpl}
 * (the {@code MethodConversion} gate, in contrast to {@link ClashMapper} which exercises the
 * {@code MethodMethod} gate with a built-in helper method).
 * <p>
 * Here the source is converted by the user method {@code toInteger} into the intermediate type
 * {@code Integer}, from which the built-in {@code Integer -> String} conversion produces the
 * target type. The helper's local variable for the intermediate result is named
 * {@code decapitalize("Integer")} = {@code integer}, exactly the same as the helper's parameter,
 * inherited from the user method's parameter name {@code integer}. Without the fix to seed
 * {@code existingVariableNames} with the parameter name, the generated helper is
 * {@code Integer integer = toInteger( integer );}, i.e. a duplicate variable that does not
 * compile under either javac or eclipse.
 */
@Mapper
public interface ConversionClashMapper {

    ConversionClashMapper INSTANCE = Mappers.getMapper( ConversionClashMapper.class );

    @Nullable
    default Integer toInteger( @Nullable ConversionClashInput integer ) {
        return integer == null ? null : 42;
    }

    @Mapping( target = "number", source = "conversionClashInput" )
    ConversionClashTargetBean map( ConversionClashSourceBean source );
}
