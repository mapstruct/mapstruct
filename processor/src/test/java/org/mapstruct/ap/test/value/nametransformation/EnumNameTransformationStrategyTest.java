/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CheeseType.class,
    CheeseTypeSuffixed.class,
    CheeseTypePrefixed.class,
    CheeseTypeCustomSuffix.class,
})
public class EnumNameTransformationStrategyTest {

    @ProcessorTest
    @WithClasses({
        CheeseSuffixMapper.class
    })
    public void shouldApplySuffixAndStripSuffixOnEnumToEnumMapping() {
        CheeseSuffixMapper mapper = CheeseSuffixMapper.INSTANCE;

        assertThat( mapper.map( CheeseType.BRIE ) )
            .isEqualTo( CheeseTypeSuffixed.BRIE_CHEESE_TYPE );
        assertThat( mapper.mapInheritInverse( CheeseTypeSuffixed.BRIE_CHEESE_TYPE ) )
            .isEqualTo( CheeseType.BRIE );
        assertThat( mapper.mapStripSuffix( CheeseTypeSuffixed.BRIE_CHEESE_TYPE ) )
            .isEqualTo( CheeseType.BRIE );
    }

    @ProcessorTest
    @WithClasses({
        CheesePrefixMapper.class
    })
    public void shouldApplyPrefixAndStripPrefixOnEnumToEnumMapping() {
        CheesePrefixMapper mapper = CheesePrefixMapper.INSTANCE;

        assertThat( mapper.map( CheeseType.BRIE ) )
            .isEqualTo( CheeseTypePrefixed.SWISS_BRIE );
        assertThat( mapper.mapInheritInverse( CheeseTypePrefixed.SWISS_BRIE ) )
            .isEqualTo( CheeseType.BRIE );
        assertThat( mapper.mapStripPrefix( CheeseTypePrefixed.SWISS_BRIE ) )
            .isEqualTo( CheeseType.BRIE );
    }

    @ProcessorTest
    @WithClasses({
        CheeseEnumToStringSuffixMapper.class
    })
    public void shouldApplySuffixAndStripSuffixOnEnumToStringMapping() {
        CheeseEnumToStringSuffixMapper mapper = CheeseEnumToStringSuffixMapper.INSTANCE;

        assertThat( mapper.map( CheeseType.BRIE ) ).isEqualTo( "BRIE_CHEESE_TYPE" );
        assertThat( mapper.map( "BRIE_CHEESE_TYPE" ) ).isEqualTo( CheeseType.BRIE );
        assertThat( mapper.mapStripSuffix( "BRIE" ) ).isEqualTo( CheeseTypeSuffixed.BRIE_CHEESE_TYPE );
        assertThat( mapper.mapStripSuffix( "DEFAULT" ) ).isEqualTo( CheeseTypeSuffixed.DEFAULT );
    }

    @ProcessorTest
    @WithClasses({
        CheeseEnumToStringPrefixMapper.class
    })
    public void shouldApplyPrefixAndStripPrefixOnEnumToStringMapping() {
        CheeseEnumToStringPrefixMapper mapper = CheeseEnumToStringPrefixMapper.INSTANCE;

        assertThat( mapper.map( CheeseType.BRIE ) ).isEqualTo( "SWISS_BRIE" );
        assertThat( mapper.map( "FRENCH_BRIE" ) ).isEqualTo( CheeseType.BRIE );
        assertThat( mapper.mapStripPrefix( "BRIE" ) ).isEqualTo( CheeseTypePrefixed.SWISS_BRIE );
        assertThat( mapper.mapStripPrefix( "DEFAULT" ) ).isEqualTo( CheeseTypePrefixed.DEFAULT );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousNameTransformStrategyMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousNameTransformStrategyMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 20,
                message = "There is no registered EnumTransformationStrategy for 'custom'. Registered strategies are:" +
                    " prefix, stripPrefix, stripSuffix, suffix."
            )
        }
    )
    public void shouldGiveCompileErrorWhenUsingUnknownNameTransformStrategy() {
    }

    @ProcessorTest
    @WithClasses({
        ErroneousNameTransformStrategyMapper.class
    })
    @WithServiceImplementation(CustomEnumTransformationStrategy.class)
    public void shouldUseCustomEnumTransformationStrategy() {
        assertThat( ErroneousNameTransformStrategyMapper.INSTANCE.map( CheeseType.BRIE ) )
            .isEqualTo( CheeseTypeCustomSuffix.brie_TYPE );
    }

}
