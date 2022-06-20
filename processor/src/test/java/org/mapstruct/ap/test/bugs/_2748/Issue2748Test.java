/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2748;

import java.util.Collections;
import java.util.Map;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2748")
@WithClasses( {
    Issue2748Mapper.class
} )
class Issue2748Test {

    @ProcessorTest
    void shouldMapNonJavaIdentifier() {
        Map<String, String> annotations = Collections.singletonMap( "specific/value", "value" );
        Issue2748Mapper.Source source = new Issue2748Mapper.Source( annotations );

        Issue2748Mapper.Target target = Issue2748Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSpecificValue() ).isEqualTo( "value" );
    }
}
