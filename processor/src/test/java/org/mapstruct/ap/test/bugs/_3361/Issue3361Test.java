/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3361;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3361")
@WithClasses(Issue3361Mapper.class)
class Issue3361Test {

    @ProcessorTest
    void multiSourceShouldInherit() {
        Issue3361Mapper.Source source = new Issue3361Mapper.Source( "Test" );
        Issue3361Mapper.OtherSource otherSource = new Issue3361Mapper.OtherSource( 10L );

        Issue3361Mapper.Target target = Issue3361Mapper.INSTANCE.mapFromSource( source, otherSource );
        assertThat( target.getSomeAttribute() ).isEqualTo( "Test" );
        assertThat( target.getOtherAttribute() ).isEqualTo( 10L );

        target = Issue3361Mapper.INSTANCE.mapInherited( source, otherSource );
        assertThat( target.getSomeAttribute() ).isEqualTo( "Test" );
        assertThat( target.getOtherAttribute() ).isEqualTo( 1L );
    }
}
