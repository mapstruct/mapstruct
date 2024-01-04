/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3144;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * @author Filip Hrisafov
 */
@WithClasses(Issue3144Mapper.class)
@IssueKey("3144")
class Issue3144Test {

    @ProcessorTest
    void shouldCorrectlyHandleMapBeanMapping() {
        Map<String, String> map = new HashMap<>();
        map.put( "value", "Map Value" );
        map.put( "testValue", "Map Test Value" );

        Issue3144Mapper.Target target = Issue3144Mapper.INSTANCE.mapExplicitDefined( map );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "Map Value" );
        assertThat( target.getMap() )
            .containsOnly(
                entry( "value", "Map Value" ),
                entry( "testValue", "Map Test Value" )
            );

        target = Issue3144Mapper.INSTANCE.map( map );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "Map Value" );
        assertThat( target.getMap() ).isNull();

        target = Issue3144Mapper.INSTANCE.mapMultiParameters( null, map );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();
        assertThat( target.getMap() )
            .containsOnly(
                entry( "value", "Map Value" ),
                entry( "testValue", "Map Test Value" )
            );

        target = Issue3144Mapper.INSTANCE.mapMultiParametersDefinedMapping( null, map );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "Map Test Value" );
        assertThat( target.getMap() ).isNull();
    }
}
