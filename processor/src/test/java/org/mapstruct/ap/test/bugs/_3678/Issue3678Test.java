/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3678;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("3678")
@WithClasses(Issue3678Mapper.class)
public class Issue3678Test {

    @ProcessorTest
    void beforeAndAfterMappingOnlyCalledOnceForTwoSources() {

        Issue3678Mapper.MappingContext mappingContext = new Issue3678Mapper.MappingContext();
        Issue3678Mapper.SourceA sourceA = new Issue3678Mapper.SourceA( "name" );
        Issue3678Mapper.SourceB sourceB = new Issue3678Mapper.SourceB( "description" );
        Issue3678Mapper.INSTANCE.map( sourceA, sourceB, mappingContext );

        assertThat( mappingContext.getInvokedMethods() )
            .containsExactly(
                "beforeMappingSourceA",
                "beforeMappingSourceB",
                "afterMappingSourceA",
                "afterMappingSourceB"
            );
    }

    @ProcessorTest
    void beforeAndAfterMappingOnlyCalledOnceForSingleSource() {

        Issue3678Mapper.MappingContext mappingContext = new Issue3678Mapper.MappingContext();
        Issue3678Mapper.SourceA sourceA = new Issue3678Mapper.SourceA( "name" );
        Issue3678Mapper.INSTANCE.map( sourceA, mappingContext );

        assertThat( mappingContext.getInvokedMethods() )
            .containsExactly(
                "beforeMappingSourceA",
                "afterMappingSourceA"
            );
    }

}
