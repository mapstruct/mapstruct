/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.collection.map.other.ImportedType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Test for implementation of {@code Map} mapping methods with @{@link org.mapstruct.Mapping Mappings}
 *
 * @author Oliver Erhart
 */
@WithClasses({
    AnnotatedSourceTargetMapper.class,
    CustomNumberMapper.class,
    Source.class,
    Target.class,
    ImportedType.class
})
@IssueKey("3303")
public class AnnotatedMapMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldContainTwoKeyMappingMapsInConstructor() {
        generatedSource.forMapper( AnnotatedSourceTargetMapper.class )
            .content()
            .contains(
                "private final Map<String, String> stringMapToStringMapUsingTargetParameterKeyMappings;" )
            .contains(
                "private final Map<String, String> stringMapToStringMapUsingTargetParameterAndReturnKeyMappings;" )
            .contains( "private final Map<String, String> stringMapWithMappingAnnotationsKeyMappings;" )
            .contains(
                "private final Map<String, String> stringMapWithMappingAnnotationsAndDifferentNameKeyMappings;" )
            .contains(
                "private final Map<String, String> stringLongMapToStringIntegerMapWithMappingAnnotationsKeyMappings;" )
            .doesNotContain( "private final Map<String, String> stringMapWithInvalidMappingAnnotationsKeyMappings;" )
            .doesNotContain( "private final Map<String, String> longMapWithMappingAnnotationsKeyMappings;" )
            .contains( expectedConstructor() );
    }

    private static String expectedConstructor() {
        return "public AnnotatedSourceTargetMapperImpl() {" +
            lineSeparator() +
            constructorInitOf( "stringMapToStringMapUsingTargetParameterKeyMappings" ) +
            lineSeparator() +
            constructorInitOf( "stringMapToStringMapUsingTargetParameterAndReturnKeyMappings" ) +
            lineSeparator() +
            constructorInitOf( "stringMapWithMappingAnnotationsKeyMappings" ) +
            lineSeparator() +
            constructorInitOf( "stringMapWithMappingAnnotationsAndDifferentNameKeyMappings" ) +
            lineSeparator() +
            constructorInitOf( "stringLongMapToStringIntegerMapWithMappingAnnotationsKeyMappings" ) +
            "    }";
    }

    private static String constructorInitOf(String parameterName) {
        return
            "        this." + parameterName + " = new java.util.HashMap<>();" +
                lineSeparator() +
                "        this." + parameterName + ".put( \"sourceKey\", \"targetKey\" );" +
                lineSeparator() +
                "        this." + parameterName + ".put( \"nonExistentSourceKey\", \"otherTargetKey\" );" +
                lineSeparator();
    }

    @ProcessorTest
    public void shouldMapBasedOnKeyMappings() {
        Map<String, String> values = new HashMap<>();
        values.put( "sourceKey", "value" );
        values.put( "unmappedKey", "otherValue" );

        Map<String, String> target = AnnotatedSourceTargetMapper.INSTANCE.stringMapWithMappingAnnotations( values );

        assertThat( target ).isNotNull()
            .hasSize( 2 )
            .contains(
                entry( "targetKey", "value" ),
                entry( "unmappedKey", "otherValue" )
            );
    }

    @ProcessorTest
    public void shouldNotMapBasedOnKeyMappingsBecauseOfTypeLong() {
        Map<Long, Long> values = new HashMap<>();
        values.put( 1L, 2L );
        values.put( 3L, 4L );

        Map<Long, Long> target = AnnotatedSourceTargetMapper.INSTANCE.longMapWithMappingAnnotations( values );

        assertThat( target ).isNotNull()
            .hasSize( 2 )
            .isEqualTo( values );
    }

}
