/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2177;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2177")
@WithClasses({
    Issue2177Mapper.class
})
public class Issue2177Test {

    @ProcessorTest
    public void shouldCorrectlyUseGenericClassesWithConstructorMapping() {

        Issue2177Mapper.Target<String> target = Issue2177Mapper.INSTANCE.map( new Issue2177Mapper.Source( "test" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
