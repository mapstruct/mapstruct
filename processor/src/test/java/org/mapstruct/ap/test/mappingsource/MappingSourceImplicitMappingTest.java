/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.test.mappingsource.MappingSourceImplicitMappingMapper.BeanSource;
import org.mapstruct.ap.test.mappingsource.MappingSourceImplicitMappingMapper.BeanTarget;
import org.mapstruct.ap.test.mappingsource.MappingSourceImplicitMappingMapper.MapTarget;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2559")
@WithClasses(MappingSourceImplicitMappingMapper.class)
public class MappingSourceImplicitMappingTest {

    @ProcessorTest
    public void testMultiSourceWithImplicitMapping() {
        Map<String, String> mapSource = new HashMap<>();
        mapSource.put( "mapId", "1" );
        mapSource.put( "mapName", "MapTest" );

        BeanSource beanSource = new MappingSourceImplicitMappingMapper.BeanSource();
        beanSource.setBeanId( 2 );
        beanSource.setBeanName( "BeanTest" );

        MapTarget mapTarget = MappingSourceImplicitMappingMapper.INSTANCE.multiSourceWithImplicitMap(
            mapSource,
            null
        );

        assertThat( mapTarget ).isNotNull();
        assertThat( mapTarget.getMapId() ).isEqualTo( "1" );
        assertThat( mapTarget.getMapName() ).isEqualTo( "MapTest" );

        BeanTarget beanTarget = MappingSourceImplicitMappingMapper.INSTANCE.multiSourceWithImplicitBean(
            beanSource,
            null
        );
        assertThat( beanTarget ).isNotNull();
        assertThat( beanTarget.getBeanId() ).isNull();
        assertThat( beanTarget.getBeanName() ).isNull();
    }

    @ProcessorTest
    public void shouldDisableImplicitMappingForSingleMapSource() {
        Map<String, String> mapSource = Map.of( "mapId", "1", "mapName", "MapTest" );

        MapTarget mapTarget = MappingSourceImplicitMappingMapper.INSTANCE.singleWithImplicitMap( mapSource );

        assertThat( mapTarget ).isNotNull();
        assertThat( mapTarget.getMapId() ).isNull();
        assertThat( mapTarget.getMapName() ).isNull();
    }
}

