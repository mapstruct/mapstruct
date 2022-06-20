/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CheeseType.class,
    CustomCheeseType.class,
    CustomEnumMarker.class,
})
@WithServiceImplementation(CustomErroneousEnumMappingStrategy.class)
public class CustomErroneousEnumMappingStrategyTest {

    @ProcessorTest
    @WithClasses({
        CustomCheeseMapper.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = CustomCheeseMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 24,
                messageRegExp = "Constant INCORRECT doesn't exist in enum type " +
                    "org\\.mapstruct\\.ap\\.test\\.value\\.spi\\.CustomCheeseType." +
                    " Constant was returned from EnumMappingStrategy: .*CustomErroneousEnumMappingStrategy@.*"
            ),
            @Diagnostic(
                type = CustomCheeseMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 33,
                messageRegExp = "Constant INCORRECT doesn't exist in enum type " +
                    "org\\.mapstruct\\.ap\\.test\\.value\\.spi\\.CustomCheeseType." +
                    " Constant was returned from EnumMappingStrategy: .*CustomErroneousEnumMappingStrategy@.*"
            )
        }
    )
    public void shouldThrowCompileErrorWhenDefaultEnumDoesNotExist() {
    }

    @ProcessorTest
    @WithClasses({
        OverridesCustomCheeseMapper.class
    })
    public void shouldApplyDefinedMappingsInsteadOfCustomEnumMappingStrategy() {
        OverridesCustomCheeseMapper mapper = OverridesCustomCheeseMapper.INSTANCE;

        // CheeseType -> CustomCheeseType
        assertThat( mapper.map( (CheeseType) null ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.map( CheeseType.BRIE ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.map( CheeseType.ROQUEFORT ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );

        // CustomCheeseType -> CheeseType
        assertThat( mapper.map( (CustomCheeseType) null ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.map( CustomCheeseType.UNSPECIFIED ) ).isNull();
        assertThat( mapper.map( CustomCheeseType.CUSTOM_BRIE ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.map( CustomCheeseType.CUSTOM_ROQUEFORT ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.map( CustomCheeseType.UNRECOGNIZED ) ).isNull();

        // CheeseType -> String
        assertThat( mapper.mapToString( (CheeseType) null ) ).isNull();
        assertThat( mapper.mapToString( CheeseType.BRIE ) ).isEqualTo( "BRIE" );
        assertThat( mapper.mapToString( CheeseType.ROQUEFORT ) ).isEqualTo( "BRIE" );

        // CustomCheeseType -> String
        assertThat( mapper.mapToString( (CustomCheeseType) null ) ).isEqualTo( "ROQUEFORT" );
        assertThat( mapper.mapToString( CustomCheeseType.UNSPECIFIED ) ).isNull();
        assertThat( mapper.mapToString( CustomCheeseType.CUSTOM_BRIE ) ).isEqualTo( "BRIE" );
        assertThat( mapper.mapToString( CustomCheeseType.CUSTOM_ROQUEFORT ) ).isEqualTo( "BRIE" );
        assertThat( mapper.mapToString( CustomCheeseType.UNRECOGNIZED ) ).isNull();

        // String - > CheeseType
        assertThat( mapper.mapStringToCheese( null ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.mapStringToCheese( "BRIE" ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.mapStringToCheese( "ROQUEFORT" ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.mapStringToCheese( "UNKNOWN" ) ).isEqualTo( CheeseType.BRIE );

        // CustomCheeseType -> String
        assertThat( mapper.mapStringToCustom( null ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.mapStringToCustom( "UNRECOGNIZED" ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
        assertThat( mapper.mapStringToCustom( "BRIE" ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.mapStringToCustom( "ROQUEFORT" ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.mapStringToCustom( "UNKNOWN" ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
    }
}
