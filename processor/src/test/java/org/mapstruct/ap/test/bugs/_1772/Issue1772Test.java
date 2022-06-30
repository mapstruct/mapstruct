/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1772;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@IssueKey("1772")
@WithClasses({
    Issue1772Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1772Test {

    @ProcessorTest
    public void shouldCorrectlyMarkSourceAsUsed() {

        Source source = new Source();
        source.setNestedSource( new Source.NestedSource() );
        source.getNestedSource().setDoublyNestedSourceField( 5d );

        Target target = Issue1772Mapper.INSTANCE.map( source );

        assertThat( target.getNestedTarget().getDoubleNestedTarget() ).isEqualTo( 5d );
    }
}
