/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2042;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2402")
@WithClasses({
    Issue2402Mapper.class
})
public class Issue2402Test {

    @ProcessorTest
    public void shouldCompile() {
        Issue2402Mapper.Target target = Issue2402Mapper.INSTANCE.
            map(
                new Issue2402Mapper.Source( new Issue2402Mapper.Info( "test" ) ),
                "other test"
            );

        assertThat( target.getName() ).isEqualTo( "test" );
    }
}
