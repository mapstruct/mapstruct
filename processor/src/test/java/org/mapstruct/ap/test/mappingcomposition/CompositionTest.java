/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.test.mappingcomposition.iterable.IterableCompositionMapper;
import org.mapstruct.ap.test.mappingcomposition.iterable.ToIterable;
import org.mapstruct.ap.test.mappingcomposition.map.MapCompositionMapper;
import org.mapstruct.ap.test.mappingcomposition.map.ToMap;
import org.mapstruct.ap.test.mappingcomposition.value.EnumCompositionMapper;
import org.mapstruct.ap.test.mappingcomposition.value.ToEnum;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("807")
public class CompositionTest {

    @ProcessorTest
    @WithClasses({
        BoxDto.class,
        BoxEntity.class,
        ShelveDto.class,
        ShelveEntity.class,
        StorageMapper.class,
        ToEntity.class
    })
    public void shouldCompose() {

        Date now = new Date();
        Date anHourAgo = new Date( now.getTime() - 3600 * 1000 );
        Date anHourFromNow = new Date( now.getTime() + 3600 * 1000 );

        ShelveDto shelve = new ShelveDto();
        shelve.setGroupName( "blue things" );
        shelve.setMaxWeight( 10.0d );
        shelve.setPath( "row5" );
        shelve.setStandNumber( 3 );

        BoxDto box = new BoxDto();
        box.setDesignation( "blue item" );
        box.setGroupName( "blue stuff" );
        box.setShelve( shelve );

        BoxEntity boxEntity = StorageMapper.INSTANCE.map( box );

        // box
        assertThat( boxEntity ).isNotNull();
        assertThat( boxEntity.getCreationDate() ).isBetween( anHourAgo, anHourFromNow );
        assertThat( boxEntity.getId() ).isNull();
        assertThat( boxEntity.getName() ).isEqualTo( "blue stuff" );
        assertThat( boxEntity.getLabel() ).isEqualTo( "blue item" );

        // shelve
        ShelveEntity shelveEntity = boxEntity.getShelve();
        assertThat( shelveEntity.getCreationDate() ).isBetween( anHourAgo, anHourFromNow );
        assertThat( shelveEntity.getId() ).isNull();
        assertThat( shelveEntity.getName() ).isEqualTo( "blue things" );
        assertThat( shelveEntity.getPath() ).isEqualTo( "row5" );
        assertThat( shelveEntity.getStandNumber() ).isEqualTo( 3 );
        assertThat( shelveEntity.getWeightLimit() ).isEqualTo( 10.0d );

    }

    @ProcessorTest
    @WithClasses( {IterableCompositionMapper.class, ToIterable.class } )
    public void shouldIterableCompositionSuccess() {
        IterableCompositionMapper compositionMapper = Mappers.getMapper( IterableCompositionMapper.class );
        List<Integer> integers = new ArrayList<>();
        integers.add( 520 );
        integers.add( 1314 );
        List<String> strings = compositionMapper.prices( integers );
        assertThat( strings.get( 0 ) ).isEqualTo( "$520.00" );
        assertThat( strings.get( 1 ) ).isEqualTo( "$1314.00" );
    }

    @ProcessorTest
    @WithClasses( {IterableCompositionMapper.class, ToIterable.class } )
    public void shouldPrioritySourceAnnotation() {
        IterableCompositionMapper compositionMapper = Mappers.getMapper( IterableCompositionMapper.class );
        List<Integer> integers = new ArrayList<>();
        integers.add( 520 );
        integers.add( 1314 );
        List<String> strings = compositionMapper.duplicateAnnotation( integers );
        assertThat( strings.get( 0 ) ).isEqualTo( "@520.00" );
        assertThat( strings.get( 1 ) ).isEqualTo( "@1314.00" );
    }

    @ProcessorTest
    @WithClasses( { MapCompositionMapper.class, ToMap.class } )
    public void shouldMapCompositionSuccess() {
        MapCompositionMapper compositionMapper = Mappers.getMapper( MapCompositionMapper.class );
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        stringIntegerMap.put( "string1", 520 );
        stringIntegerMap.put( "string2", 1314 );
        Map<String, String> stringStringMap = compositionMapper.stringIntegerMapToStringStringMap( stringIntegerMap );
        assertThat( stringStringMap.get( "string1" ) ).isEqualTo( "$520.00" );
        assertThat( stringStringMap.get( "string2" ) ).isEqualTo( "$1314.00" );
    }

    @ProcessorTest
    @WithClasses( {EnumCompositionMapper.class, ExternalOrderType.class, OrderType.class, ToEnum.class} )
    public void shouldEnumCompositionSuccess() {
        EnumCompositionMapper compositionMapper = Mappers.getMapper( EnumCompositionMapper.class );
        assertThat( compositionMapper.orderTypeToExternalOrderType( OrderType.EXTRA ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.orderTypeToExternalOrderType( OrderType.NORMAL ) )
            .isEqualTo( ExternalOrderType.DEFAULT );
    }

    @ProcessorTest
    @WithClasses( {EnumCompositionMapper.class, ExternalOrderType.class, OrderType.class, ToEnum.class} )
    public void duplicateValueMappingAnnotation() {
        EnumCompositionMapper compositionMapper = Mappers.getMapper( EnumCompositionMapper.class );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.EXTRA ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.STANDARD ) )
            .isEqualTo( ExternalOrderType.SPECIAL );
        assertThat( compositionMapper.duplicateAnnotation( OrderType.NORMAL ) )
            .isEqualTo( ExternalOrderType.DEFAULT );

    }
}
