/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4000;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Issue4000Mapper.class,
    Source.class,
    Target.class,
})
@IssueKey("4000")
public class Issue4000Test {

    @ProcessorTest
    public void fluentSetterWithSecondCharUpperCaseShouldMatchJavaBeanAccessor() {
        Target target = Issue4000Mapper.INSTANCE.map( new Source( "value" ) );

        assertThat( target.getXNameField() ).isEqualTo( "value" );
    }
}
