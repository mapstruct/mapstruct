/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.bugs._374;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import static org.fest.assertions.Assertions.assertThat;

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

        List<String> targetList = new ArrayList<String>();
        targetList.add( "test" );
        List<String> resultList = Issue374Mapper.INSTANCE.mapIterable( null, targetList );
        assertThat( resultList ).isEqualTo( targetList );
        assertThat( targetList ).isEmpty();
    }

    @Test
    @WithClasses( { Issue374Mapper.class, Source.class, Target.class } )
    public void shouldMapExistingMapTargetToDefault() {

        Map<Integer, String> targetMap = new HashMap<Integer, String>();
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

        List<String> targetList = new ArrayList<String>();
        targetList.add( "test" );
        Issue374VoidMapper.INSTANCE.mapIterable( null, targetList );
        assertThat( targetList ).isEmpty();
    }

    @Test
    @WithClasses( { Issue374VoidMapper.class, Source.class, Target.class } )
    public void shouldMapExistingMapTargetVoidReturnToDefault() {

        Map<Integer, String> targetMap = new HashMap<Integer, String>();
        targetMap.put( 5, "test" );
        Issue374VoidMapper.INSTANCE.mapMap( null, targetMap );
        assertThat( targetMap ).isEmpty();

    }
}
