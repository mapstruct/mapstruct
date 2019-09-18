/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1790;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1790")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1790Config.class,
    Issue1790Mapper.class,
    Source.class,
    Target.class,
})
public class Issue1790Test {

    @Test
    public void shouldProperlyApplyNullValuePropertyMappingStrategyWhenInheriting() {
        Target target = new Target();
        target.setName( "My name is set" );

        Source source = new Source();

        Issue1790Mapper.INSTANCE.toExistingCar( target, source );

        assertThat( target.getName() ).isEqualTo( "My name is set" );
    }
}
