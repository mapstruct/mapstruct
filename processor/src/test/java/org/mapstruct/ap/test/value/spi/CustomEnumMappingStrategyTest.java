/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithServiceImplementation;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CheeseType.class,
    CustomCheeseType.class,
    CustomEnumMarker.class,
    CustomThrowingCheeseType.class,
    CustomThrowingEnumMarker.class,
    CustomIllegalArgumentException.class,
})
@WithServiceImplementation(CustomEnumMappingStrategy.class)
public class CustomEnumMappingStrategyTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        CustomCheeseMapper.class
    })
    public void shouldApplyCustomEnumMappingStrategy() {
        generatedSource.addComparisonToFixtureFor( CustomCheeseMapper.class );
        CustomCheeseMapper mapper = CustomCheeseMapper.INSTANCE;

        // CheeseType -> CustomCheeseType
        assertThat( mapper.map( (CheeseType) null ) ).isEqualTo( CustomCheeseType.UNSPECIFIED );
        assertThat( mapper.map( CheeseType.BRIE ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
        assertThat( mapper.map( CheeseType.ROQUEFORT ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );

        // CustomCheeseType -> CheeseType
        assertThat( mapper.map( (CustomCheeseType) null ) ).isNull();
        assertThat( mapper.map( CustomCheeseType.UNSPECIFIED ) ).isNull();
        assertThat( mapper.map( CustomCheeseType.CUSTOM_BRIE ) ).isEqualTo( CheeseType.BRIE );
        assertThat( mapper.map( CustomCheeseType.CUSTOM_ROQUEFORT ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.map( CustomCheeseType.UNRECOGNIZED ) ).isNull();

        // CheeseType -> String
        assertThat( mapper.mapToString( (CheeseType) null ) ).isNull();
        assertThat( mapper.mapToString( CheeseType.BRIE ) ).isEqualTo( "BRIE" );
        assertThat( mapper.mapToString( CheeseType.ROQUEFORT ) ).isEqualTo( "ROQUEFORT" );

        // CustomCheeseType -> String
        assertThat( mapper.mapToString( (CustomCheeseType) null ) ).isNull();
        assertThat( mapper.mapToString( CustomCheeseType.UNSPECIFIED ) ).isNull();
        assertThat( mapper.mapToString( CustomCheeseType.CUSTOM_BRIE ) ).isEqualTo( "BRIE" );
        assertThat( mapper.mapToString( CustomCheeseType.CUSTOM_ROQUEFORT ) ).isEqualTo( "ROQUEFORT" );
        assertThat( mapper.mapToString( CustomCheeseType.UNRECOGNIZED ) ).isNull();

        // String - > CheeseType
        assertThat( mapper.mapStringToCheese( null ) ).isNull();
        assertThat( mapper.mapStringToCheese( "BRIE" ) ).isEqualTo( CheeseType.BRIE );
        assertThat( mapper.mapStringToCheese( "ROQUEFORT" ) ).isEqualTo( CheeseType.ROQUEFORT );
        assertThat( mapper.mapStringToCheese( "UNKNOWN" ) ).isEqualTo( CheeseType.BRIE );

        // CustomCheeseType -> String
        assertThat( mapper.mapStringToCustom( null ) ).isEqualTo( CustomCheeseType.UNSPECIFIED );
        assertThat( mapper.mapStringToCustom( "UNRECOGNIZED" ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
        assertThat( mapper.mapStringToCustom( "BRIE" ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
        assertThat( mapper.mapStringToCustom( "ROQUEFORT" ) ).isEqualTo( CustomCheeseType.CUSTOM_ROQUEFORT );
        assertThat( mapper.mapStringToCustom( "UNKNOWN" ) ).isEqualTo( CustomCheeseType.CUSTOM_BRIE );
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

    @ProcessorTest
    @IssueKey("2339")
    @WithClasses({
        CustomThrowingCheeseMapper.class
    })
    public void shouldApplyCustomEnumMappingStrategyWithThrowingException() {
        CustomThrowingCheeseMapper mapper = CustomThrowingCheeseMapper.INSTANCE;

        // CustomCheeseType -> CustomThrowingCheeseType
        assertThatThrownBy( () -> mapper.map( (CheeseType) null ) )
            .isInstanceOf( CustomIllegalArgumentException.class )
            .hasMessage( "Unexpected enum constant: null" );
        assertThat( mapper.map( CheeseType.BRIE ) ).isEqualTo( CustomThrowingCheeseType.CUSTOM_BRIE );

        // CustomThrowingCheeseType -> CustomCheeseType
        assertThat( mapper.map( (CustomThrowingCheeseType) null ) ).isNull();
        assertThat( mapper.map( CustomThrowingCheeseType.CUSTOM_BRIE ) ).isEqualTo( CheeseType.BRIE );
    }
}
