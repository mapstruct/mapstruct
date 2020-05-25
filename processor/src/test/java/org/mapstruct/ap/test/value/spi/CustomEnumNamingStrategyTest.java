/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    CheeseType.class,
    CustomCheeseType.class,
    CustomEnumMarker.class,
})
@WithServiceImplementation(CustomEnumNamingStrategy.class)
public class CustomEnumNamingStrategyTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({
        CustomCheeseMapper.class
    })
    public void shouldApplyCustomEnumNamingStrategy() {
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

    @Test
    @WithClasses({
        OverridesCustomCheeseMapper.class
    })
    public void shouldApplyDefinedMappingsInsteadOfCustomEnumNamingStrategy() {
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
