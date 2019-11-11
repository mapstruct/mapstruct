/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._374;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/306.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "306" )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue374Test {

    @Test
    @WithClasses( { Issue374Mapper.class, Source.class, Target.class } )
    public void shouldMapExistingTargetToDefault() {

        Target target = new Target();
        Target result = Issue374Mapper.INSTANCE.map( null, target );
        assertThat( result ).isEqualTo( target );
        assertThat( result.getTest() ).isNull();
        assertThat( result.getConstant() ).isEqualTo( "test" );
    }

    @Test
    @WithClasses( { Issue374Mapper.class, Source.class, Target.class } )
    public void shouldMapExistingTargetWithConstantToDefault() {

        Target target2 = new Target();
        Target result2 = Issue374Mapper.INSTANCE.map2( null, target2 );
        assertThat( result2 ).isNull();
        assertThat( target2.getTest() ).isNull();
        assertThat( target2.getConstant() ).isNull();
    }

    @Test
    @WithClasses( { Issue374Mapper.class, Source.class, Target.class } )
    public void shouldMapExistingIterableTargetToDefault() {

        List<String> targetList = new ArrayList<>();
        targetList.add( "test" );
        List<String> resultList = Issue374Mapper.INSTANCE.mapIterable( null, targetList );
        assertThat( resultList ).isEqualTo( targetList );
        assertThat( targetList ).isEmpty();
    }

    @Test
    @WithClasses( { Issue374Mapper.class, Source.class, Target.class } )
    public void shouldMapExistingMapTargetToDefault() {

        Map<Integer, String> targetMap = new HashMap<>();
        targetMap.put( 5, "test" );
        Map<Integer, String> resultMap  = Issue374Mapper.INSTANCE.mapMap( null, targetMap );
        assertThat( resultMap ).isEmpty();
        assertThat( resultMap ).isEqualTo( resultMap );
    }

    @Test
    @WithClasses( { Issue374VoidMapper.class, Source.class, Target.class } )
    public void shouldMapExistingTargetVoidReturnToDefault() {

        Target target = new Target();
        Issue374VoidMapper.INSTANCE.map( null, target );
        assertThat( target.getTest() ).isNull();
        assertThat( target.getConstant() ).isEqualTo( "test" );
    }

    @Test
    @WithClasses( { Issue374VoidMapper.class, Source.class, Target.class } )
    public void shouldMapExistingIterableTargetVoidReturnToDefault() {

        List<String> targetList = new ArrayList<>();
        targetList.add( "test" );
        Issue374VoidMapper.INSTANCE.mapIterable( null, targetList );
        assertThat( targetList ).isEmpty();
    }

    @Test
    @WithClasses( { Issue374VoidMapper.class, Source.class, Target.class } )
    public void shouldMapExistingMapTargetVoidReturnToDefault() {

        Map<Integer, String> targetMap = new HashMap<>();
        targetMap.put( 5, "test" );
        Issue374VoidMapper.INSTANCE.mapMap( null, targetMap );
        assertThat( targetMap ).isEmpty();

    }
}
