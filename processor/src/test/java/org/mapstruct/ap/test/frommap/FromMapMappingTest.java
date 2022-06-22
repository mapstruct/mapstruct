/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * @author Christian Kosmowski
 */
@IssueKey("1075")
class FromMapMappingTest {

    @Nested
    @WithClasses({
        StringMapToBeanMapper.class
    })
    class StringMapToBeanTests {

        @ProcessorTest
        void fromNullMap() {
            assertThat( StringMapToBeanMapper.INSTANCE.fromMap( null ) ).isNull();
        }

        @ProcessorTest
        void fromEmptyMap() {
            StringMapToBeanMapper.Order order = StringMapToBeanMapper.INSTANCE.fromMap( Collections.emptyMap() );

            assertThat( order ).isNotNull();
            assertThat( order.getName() ).isNull();
            assertThat( order.getPrice() ).isEqualTo( 0.0 );
            assertThat( order.getOrderDate() ).isNull();
            assertThat( order.getShipmentDate() ).isNull();
        }

        @ProcessorTest
        void fromFullMap() {
            Map<String, String> map = new HashMap<>();
            map.put( "name", "Jacket" );
            map.put( "price", "25.5" );
            map.put( "shipmentDate", "2021-06-15" );
            StringMapToBeanMapper.Order order = StringMapToBeanMapper.INSTANCE.fromMap( map );

            assertThat( order ).isNotNull();
            assertThat( order.getName() ).isEqualTo( "Jacket" );
            assertThat( order.getPrice() ).isEqualTo( 25.5 );
            assertThat( order.getOrderDate() ).isNull();
            assertThat( order.getShipmentDate() ).isEqualTo( LocalDate.of( 2021, Month.JUNE, 15 ) );
        }

        @ProcessorTest
        void fromMapWithEmptyValuesForString() {
            Map<String, String> map = Collections.singletonMap( "name", "" );
            StringMapToBeanMapper.Order order = StringMapToBeanMapper.INSTANCE.fromMap( map );

            assertThat( order ).isNotNull();
            assertThat( order.getName() ).isEqualTo( "" );
            assertThat( order.getPrice() ).isEqualTo( 0 );
            assertThat( order.getOrderDate() ).isNull();
            assertThat( order.getShipmentDate() ).isNull();
        }

        @ProcessorTest
        void fromMapWithEmptyValuesForDouble() {
            Map<String, String> map = Collections.singletonMap( "price", "" );
            assertThatThrownBy( () -> StringMapToBeanMapper.INSTANCE.fromMap( map ) )
                .isInstanceOf( NumberFormatException.class );
        }

        @ProcessorTest
        void fromMapWithEmptyValuesForDate() {
            Map<String, String> map = Collections.singletonMap( "orderDate", "" );
            assertThatThrownBy( () -> StringMapToBeanMapper.INSTANCE.fromMap( map ) )
                .isInstanceOf( RuntimeException.class )
                .getCause()
                .isInstanceOf( ParseException.class );
        }

        @ProcessorTest
        void fromMapWithEmptyValuesForLocalDate() {
            Map<String, String> map = Collections.singletonMap( "shipmentDate", "" );
            assertThatThrownBy( () -> StringMapToBeanMapper.INSTANCE.fromMap( map ) )
                .isInstanceOf( DateTimeParseException.class );
        }
    }

    @Nested
    @WithClasses({
        StringMapToBeanWithCustomPresenceCheckMapper.class
    })
    class StringMapToBeanWithCustomPresenceCheckTests {

        @ProcessorTest
        void fromFullMap() {
            Map<String, String> map = new HashMap<>();
            map.put( "name", "Jacket" );
            map.put( "price", "25.5" );
            map.put( "shipmentDate", "2021-06-15" );
            StringMapToBeanWithCustomPresenceCheckMapper.Order order =
                StringMapToBeanWithCustomPresenceCheckMapper.INSTANCE.fromMap( map );

            assertThat( order ).isNotNull();
            assertThat( order.getName() ).isEqualTo( "Jacket" );
            assertThat( order.getPrice() ).isEqualTo( 25.5 );
            assertThat( order.getOrderDate() ).isNull();
            assertThat( order.getShipmentDate() ).isEqualTo( LocalDate.of( 2021, Month.JUNE, 15 ) );
        }

        @ProcessorTest
        void fromMapWithEmptyValuesForString() {
            Map<String, String> map = new HashMap<>();
            map.put( "name", "" );
            map.put( "price", "" );
            map.put( "orderDate", "" );
            map.put( "shipmentDate", "" );
            StringMapToBeanWithCustomPresenceCheckMapper.Order order =
                StringMapToBeanWithCustomPresenceCheckMapper.INSTANCE.fromMap( map );

            assertThat( order ).isNotNull();
            assertThat( order.getName() ).isNull();
            assertThat( order.getPrice() ).isEqualTo( 0 );
            assertThat( order.getOrderDate() ).isNull();
            assertThat( order.getShipmentDate() ).isNull();
        }

    }

    @ProcessorTest
    @WithClasses(MapToBeanDefinedMapper.class)
    void shouldMapWithDefinedMapping() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put( "number", 44 );

        MapToBeanDefinedMapper.Target target = MapToBeanDefinedMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "44" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanImplicitMapper.class)
    void shouldMapWithImpicitMapping() {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put( "name", "mapstruct" );

        MapToBeanImplicitMapper.Target target = MapToBeanImplicitMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "mapstruct" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanUpdateImplicitMapper.class)
    void shouldMapToExistingTargetWithImplicitMapping() {
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

    @ProcessorTest
    @WithClasses(MapToBeanWithDefaultMapper.class)
    void shouldMapWithDefaultValue() {
        Map<String, Integer> sourceMap = new HashMap<>();

        MapToBeanWithDefaultMapper.Target target = MapToBeanWithDefaultMapper.INSTANCE
            .toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "4711" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanUsingMappingMethodMapper.class)
    void shouldMapUsingMappingMethod() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put( "number", 23 );

        MapToBeanUsingMappingMethodMapper.Target target = MapToBeanUsingMappingMethodMapper.INSTANCE
            .toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getNormalInt() ).isEqualTo( "converted_23" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanFromMultipleSources.class)
    void shouldMapFromMultipleSources() {
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

    @ProcessorTest
    @WithClasses(MapToBeanFromMapAndNestedSource.class)
    void shouldMapFromNestedSource() {
        Map<String, Integer> integers = new HashMap<>();
        integers.put( "number", 23 );

        MapToBeanFromMapAndNestedSource.Source source = new MapToBeanFromMapAndNestedSource.Source();

        MapToBeanFromMapAndNestedSource.Target target = MapToBeanFromMapAndNestedSource.INSTANCE
            .toTarget( integers, source );

        assertThat( target ).isNotNull();
        assertThat( target.getInteger() ).isEqualTo( 23 );
        assertThat( target.getStringFromNestedSource() ).isEqualTo( "nestedString" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanFromMapAndNestedMap.class)
    void shouldMapFromNestedMap() {

        MapToBeanFromMapAndNestedMap.Source source = new MapToBeanFromMapAndNestedMap.Source();
        MapToBeanFromMapAndNestedMap.Target target = MapToBeanFromMapAndNestedMap.INSTANCE
            .toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNestedTarget() ).isNotNull();
        assertThat( target.getNestedTarget().getStringFromNestedMap() ).isEqualTo( "valueFromNestedMap" );
    }

    @IssueKey("2553")
    @ProcessorTest
    @WithClasses(MapToBeanFromMapAndNestedMapWithDefinedMapping.class)
    void shouldMapFromNestedMapWithDefinedMapping() {

        MapToBeanFromMapAndNestedMapWithDefinedMapping.Source source =
            new MapToBeanFromMapAndNestedMapWithDefinedMapping.Source();
        MapToBeanFromMapAndNestedMapWithDefinedMapping.Target target =
            MapToBeanFromMapAndNestedMapWithDefinedMapping.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNested() ).isEqualTo( "valueFromNestedMap" );
    }

    @ProcessorTest
    @WithClasses(ObjectMapToBeanWithQualifierMapper.class)
    void shouldUseObjectQualifiedMethod() {
        Map<String, Object> vehicles = new HashMap<>();
        vehicles.put( "car", new ObjectMapToBeanWithQualifierMapper.Car( "Tesla" ) );

        ObjectMapToBeanWithQualifierMapper.VehiclesDto dto =
            ObjectMapToBeanWithQualifierMapper.INSTANCE.map( vehicles );

        assertThat( dto.getCar() ).isNotNull();
        assertThat( dto.getCar() ).isInstanceOf( ObjectMapToBeanWithQualifierMapper.CarDto.class );
        assertThat( dto.getCar().getBrand() ).isEqualTo( "Tesla" );
    }

    @ProcessorTest
    @WithClasses(MapToBeanTypeCheckMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                type = MapToBeanTypeCheckMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                message = "Unmapped target property: \"value\"."
            ),
            @Diagnostic(
                type = MapToBeanTypeCheckMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                message = "The Map parameter \"source\" cannot be used for property mapping. " +
                    "It must be typed with Map<String, ???> but it was typed with Map<Integer,String>."
            ),
        }
    )
    void shouldWarnAboutWrongMapTypes() {

        Map<Integer, String> upsideDownMap = new HashMap<>();
        upsideDownMap.put( 23, "number" );

        MapToBeanTypeCheckMapper.Target target = MapToBeanTypeCheckMapper.INSTANCE
            .toTarget( upsideDownMap );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();
    }

    @ProcessorTest
    @WithClasses(MapToBeanRawMapMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                type = MapToBeanRawMapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                message = "Unmapped target property: \"value\"."
            ),
            @Diagnostic(
                type = MapToBeanRawMapMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 21,
                message = "The Map parameter \"source\" cannot be used for property mapping. " +
                    "It must be typed with Map<String, ???> but it was raw."
            ),
        }
    )
    void shouldWarnAboutRawMapTypes() {

        Map<String, String> rawMap = new HashMap<>();
        rawMap.put( "value", "number" );

        MapToBeanRawMapMapper.Target target = MapToBeanRawMapMapper.INSTANCE
            .toTarget( rawMap );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();
    }

    @ProcessorTest
    @WithClasses({
        MapToBeanNonStringMapAsMultiSourceMapper.class
    })
    void shouldNotWarnIfMappedIsUsedAsSourceParameter() {
        MapToBeanNonStringMapAsMultiSourceMapper.Target target = MapToBeanNonStringMapAsMultiSourceMapper.INSTANCE
            .toTarget(
                new MapToBeanNonStringMapAsMultiSourceMapper.Source( "test" ),
                Collections.singletonMap( 10, "value" )
            );

        assertThat( target.getValue() ).isEqualTo( "test" );
        assertThat( target.getMap() )
            .containsOnly( entry( "10", "value" ) );
    }

    @ProcessorTest
    @WithClasses(MapToBeanImplicitUnmappedSourcePolicyMapper.class)
    void shouldNotReportUnmappedSourcePropertiesWithMap() {
        Map<String, String> sourceMap = new HashMap<>();
        sourceMap.put( "name", "mapstruct" );

        MapToBeanImplicitUnmappedSourcePolicyMapper.Target target =
            MapToBeanImplicitUnmappedSourcePolicyMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "mapstruct" );
    }

}
