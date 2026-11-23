/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.localvarclash;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * Reproduces the {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} bug in the
 * "method then built-in conversion" shape, i.e. the second gate site in
 * {@code org.mapstruct.ap.internal.processor.creation.MappingResolverImpl} (the
 * {@code MethodConversion} one; {@link LocalVarClashNullSafe2StepTest} covers the first,
 * {@code MethodMethod} gate site).
 * </p>
 * <p>
 * The helper's local variable for the intermediate result is named
 * {@code decapitalize("Integer")} = {@code integer}, while the helper's parameter — inherited
 * from the user method {@code toInteger} — is also named {@code integer}. The generated helper
 * is therefore {@code Integer integer = toInteger( integer ); ...}, i.e. two variables with the
 * same name in one scope, which does not compile under either javac or eclipse.
 * </p>
 */
@WithJSpecify
@WithClasses( {
    ConversionClashInput.class,
    ConversionClashSourceBean.class,
    ConversionClashTargetBean.class,
    ConversionClashMapper.class
} )
class ConversionClashNullSafe2StepTest {

    @ProcessorTest
    void nullSafe2StepLocalVarClash() {
        ConversionClashMapper mapper = ConversionClashMapper.INSTANCE;

        // null input -> null target
        ConversionClashSourceBean nullSource = new ConversionClashSourceBean();
        ConversionClashTargetBean nullTarget = mapper.map( nullSource );
        assertThat( nullTarget.getNumber() ).isNull();

        // filled input -> the fixed number
        ConversionClashSourceBean filledSource = new ConversionClashSourceBean();
        filledSource.setConversionClashInput( new ConversionClashInput() );
        ConversionClashTargetBean filledTarget = mapper.map( filledSource );
        assertThat( filledTarget.getNumber() ).isEqualTo( "42" );
    }
}
