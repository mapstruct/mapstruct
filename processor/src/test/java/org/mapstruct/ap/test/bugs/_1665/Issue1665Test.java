/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1665;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Arne Seime
 */
@IssueKey("1665")
@WithClasses({
    Issue1665Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1665Test {

    @ProcessorTest
    public void shouldBoxIntPrimitive() {
        Source source = new Source();
        List<Integer> values = new ArrayList<>();
        values.add( 10 );
        source.setValue( values );

        Target target = Issue1665Mapper.INSTANCE.map( source );

        assertThat( target.getValue().size() ).isEqualTo( source.getValue().size() );
        assertThat( target.getValue().get( 0 ) ).isEqualTo( source.getValue().get( 0 ) );
    }
}
