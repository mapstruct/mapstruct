/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Kosmowski
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1075")
public class FromMapMappingTest {

    @Test
    @WithClasses(MapToBeanDefinedMapper.class)
    public void shouldMapWithDefinedMapping() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put( "number", 44 );

        MapToBeanDefinedMapper.Target target = MapToBeanDefinedMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "44" );
    }

    @Test
    @WithClasses(MapToBeanImplicitMapper.class)
    public void shouldMapWithImpicitMapping() {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put( "name", "mapstruct" );

        MapToBeanImplicitMapper.Target target = MapToBeanImplicitMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "mapstruct" );
    }

    @Test
    @WithClasses(MapToBeanUpdateImplicitMapper.class)
    public void shouldMapToExistingTargetWithImpicitMapping() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put( "rating",  5 );

        MapToBeanUpdateImplicitMapper.Target existingTarget = new MapToBeanUpdateImplicitMapper.Target();
        existingTarget.setRating( 4 );
        existingTarget.setName( "mapstruct" );

        MapToBeanUpdateImplicitMapper.Target target = MapToBeanUpdateImplicitMapper.INSTANCE
            .toTarget( existingTarget, sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "mapstruct" );
        assertThat( target.getRating() ).isEqualTo( 5 );
    }

    @Test
    @WithClasses(MapToBeanWithDefaultMapper.class)
    public void shouldMapWithDefaultValue() {
        Map<String, Integer> sourceMap = new HashMap<>();

        MapToBeanWithDefaultMapper.Target target = MapToBeanWithDefaultMapper.INSTANCE
            .toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "4711" );
    }

    @Test
    @WithClasses(MapToBeanUsingMappingMethodMapper.class)
    public void shouldMapUsingMappingMethod() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put( "number", 23 );

        MapToBeanUsingMappingMethodMapper.Target target = MapToBeanUsingMappingMethodMapper.INSTANCE
            .toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "converted_23" );
    }

    @Test
    @WithClasses(MapToBeanFromMultipleSources.class)
    public void shouldMapFromMultipleSources() {
        Map<String, Integer> integers = new HashMap<>();
        integers.put( "number", 23 );

        Map<String, String> strings = new HashMap<>();
        strings.put( "string", "stringFromMap" );

        MapToBeanFromMultipleSources.Source source = new MapToBeanFromMultipleSources.Source();

        MapToBeanFromMultipleSources.Target target = MapToBeanFromMultipleSources.INSTANCE
            .toTarget( integers, strings, source );

        assertThat( target ).isNotNull();
        assertThat( target.getInteger() ).isEqualTo( 23 );
        assertThat( target.getString() ).isEqualTo( "stringFromMap" );
        assertThat( target.getStringFromBean() ).isEqualTo( "stringFromBean" );
    }

    @Test
    @WithClasses(MapToBeanFromMapAndNestedSource.class)
    public void shouldMapFromNestedSource() {
        Map<String, Integer> integers = new HashMap<>();
        integers.put( "number", 23 );

        MapToBeanFromMapAndNestedSource.Source source = new MapToBeanFromMapAndNestedSource.Source();

        MapToBeanFromMapAndNestedSource.Target target = MapToBeanFromMapAndNestedSource.INSTANCE
            .toTarget( integers, source );

        assertThat( target ).isNotNull();
        assertThat( target.getInteger() ).isEqualTo( 23 );
        assertThat( target.getStringFromNestedSource() ).isEqualTo( "nestedString" );
    }

}
