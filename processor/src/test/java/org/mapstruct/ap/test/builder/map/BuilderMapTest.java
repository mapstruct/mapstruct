/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
        org.mapstruct.ap.test.builder.map.BaseDto.class,
        org.mapstruct.ap.test.builder.map.BaseEntity.class,
        org.mapstruct.ap.test.builder.map.BuilderMapMapper.class
})
public class BuilderMapTest {

    @Test
    public void shouldMapMap() {
        BaseDto source = new BaseDto();

        source.setId( 100L );

        Map<String, String> testMap = new HashMap<>();
        testMap.put( "test-key", "test-value" );
        //source.setValues( testMap );

        BaseEntity target = BuilderMapMapper.INSTANCE.map( source );

        assertThat( target.getId() ).isEqualTo( 100L );
    }
}
