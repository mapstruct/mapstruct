/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Kosmowski
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey( "1075" )
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
public class FromMapMappingTest {

    @Test
    public void shouldMapSourceToTarget() {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("theInt", "1");
        sourceMap.put("fieldWithMethods", "aTestValue");

        Target target = SourceTargetMapper.INSTANCE.toTarget( sourceMap );

        assertThat( target ).isNotNull();
        assertThat( target.normalInt ).isEqualTo( "1" );
        assertThat( target.fieldWithMethods ).isEqualTo( "aTestValue11" );
    }

    @Test
    public void shouldMapSourcesToTarget() {
        Map<String, Object> sourceMap = new HashMap<>();
        sourceMap.put("theInt", "1");
        sourceMap.put("fieldWithMethods", "aTestValue");

        Source source = new Source();
        source.normalList = new ArrayList<>();
        source.fieldOnlyWithGetter = 12;

        Target target = SourceTargetMapper.INSTANCE.toTarget( sourceMap, source );

        assertThat( target ).isNotNull();
        assertThat( target.normalInt ).isEqualTo( "1" );
        assertThat( target.fieldWithMethods ).isEqualTo( "aTestValue11" );
        assertThat( target.fieldOnlyWithGetter ).isEqualTo( 33 );
    }

}
