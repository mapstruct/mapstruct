/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses( { NoSetterMapper.class, NoSetterSource.class, NoSetterTarget.class } )
public class NoSetterCollectionMappingTest {

    @ProcessorTest
    @IssueKey( "220" )
    public void compilesAndMapsCorrectly() {
        NoSetterSource source = new NoSetterSource();
        source.setListValues( Arrays.asList( "foo", "bar" ) );
        HashMap<String, String> mapValues = new HashMap<>();
        mapValues.put( "fooKey", "fooVal" );
        mapValues.put( "barKey", "barVal" );

        source.setMapValues( mapValues );
        NoSetterTarget target = NoSetterMapper.INSTANCE.toTarget( source );

        assertThat( target.getListValues() ).containsExactly( "foo", "bar" );
        assertThat( target.getMapValues() ).contains( entry( "fooKey", "fooVal" ), entry( "barKey", "barVal" ) );

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
        //assertThat( target2.getMapValues() ).contains( entry( "fooKey", "fooVal" ), entry( "barKey", "barVal" ) );


    }
}
