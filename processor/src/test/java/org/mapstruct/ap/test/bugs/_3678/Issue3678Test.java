/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3678;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3678")
public class Issue3678Test {

    @WithClasses({
        Issue3678Mapper.class,
    })
    @ProcessorTest
    void ignoreMappingsWithoutSourceShouldBeInvertible() {

        Issue3678Mapper mapper = Mappers.getMapper( Issue3678Mapper.class );
        Issue3678Mapper.SimpleSource source = new Issue3678Mapper.SimpleSource( "name", "description" );
        Issue3678Mapper.SimpleDestination simpleDestination = mapper.sourceToDestination( source );

        assertThat( mapper.getInvocations() )
            .containsExactly( "beforeMapping", "afterMapping" )
            .doesNotHaveDuplicates();
    }

}
