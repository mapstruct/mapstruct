/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisfaov
 *
 */
@WithClasses({ NoSetterMapper.class, NoSetterSource.class, NoSetterTarget.class })
@IssueKey("962")
public class NoSetterStreamMappingTest {

    @ProcessorTest
    public void compilesAndMapsCorrectly() {
        NoSetterSource source = new NoSetterSource();
        source.setListValues( Stream.of( "foo", "bar" ) );

        NoSetterTarget target = NoSetterMapper.INSTANCE.toTarget( source );

        assertThat( target.getListValues() ).containsExactly( "foo", "bar" );

        // now test existing instances

        NoSetterSource source2 = new NoSetterSource();
        source2.setListValues( Stream.of( "baz" ) );
        List<String> originalCollectionInstance = target.getListValues();

        NoSetterTarget target2 = NoSetterMapper.INSTANCE.toTargetWithExistingTarget( source2, target );

        assertThat( target2.getListValues() ).isSameAs( originalCollectionInstance );
        assertThat( target2.getListValues() ).containsExactly( "baz" );
    }
}
