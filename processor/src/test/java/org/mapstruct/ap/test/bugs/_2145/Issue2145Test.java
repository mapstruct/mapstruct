/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2145;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2145")
@WithClasses(Issue2145Mapper.class)
public class Issue2145Test {

    @ProcessorTest
    public void test() {
        Issue2145Mapper.Source source = new Issue2145Mapper.Source();
        source.setValue( "test" );

        Issue2145Mapper.Target target = Issue2145Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested().getScope() ).isEqualTo( Issue2145Mapper.Target.class );
        assertThat( target.getNested().getValue() ).isNotNull();
        assertThat( target.getNested().getValue().getValue() ).isEqualTo( "test" );
    }
}
