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
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.assertions.MapAssert.entry;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses( { NoSetterMapper.class, NoSetterSource.class, NoSetterTarget.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class NoSetterCollectionMappingTest {

    @Test
    @IssueKey( "220" )
    public void compilesAndMapsCorrectly() {
        NoSetterSource source = new NoSetterSource();
        source.setListValues( Arrays.asList( "foo", "bar" ) );
        HashMap<String, String> mapValues = new HashMap<String, String>();
        mapValues.put( "fooKey", "fooVal" );
        mapValues.put( "barKey", "barVal" );

        source.setMapValues( mapValues );
        NoSetterTarget target = NoSetterMapper.INSTANCE.toTarget( source );

        assertThat( target.getListValues() ).containsExactly( "foo", "bar" );
        assertThat( target.getMapValues() ).includes( entry( "fooKey", "fooVal" ), entry( "barKey", "barVal" ) );

        // now test existing instances

        NoSetterSource source2 = new NoSetterSource();
        source2.setListValues( Arrays.asList( "baz" ) );
        List<String> originalCollectionInstance = target.getListValues();
        Map<String, String> originalMapInstance = target.getMapValues();

        NoSetterTarget target2 = NoSetterMapper.INSTANCE.toTargetWithExistingTarget( source2, target );

        assertThat( target2.getListValues() ).isSameAs( originalCollectionInstance );
        assertThat( target2.getListValues() ).containsExactly( "baz" );
        assertThat( target2.getMapValues() ).isSameAs( originalMapInstance );
        // source2 mapvalues is empty, so the map is not cleared
        assertThat( target2.getMapValues() ).includes( entry( "fooKey", "fooVal" ), entry( "barKey", "barVal" ) );


    }
}
