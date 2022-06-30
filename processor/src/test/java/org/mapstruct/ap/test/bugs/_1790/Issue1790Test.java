/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1790;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1790")
@WithClasses({
    Issue1790Config.class,
    Issue1790Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1790Test {

    @ProcessorTest
    public void shouldProperlyApplyNullValuePropertyMappingStrategyWhenInheriting() {
        Target target = new Target();
        target.setName( "My name is set" );

        Source source = new Source();

        Issue1790Mapper.INSTANCE.toExistingCar( target, source );

        assertThat( target.getName() ).isEqualTo( "My name is set" );
    }
}
