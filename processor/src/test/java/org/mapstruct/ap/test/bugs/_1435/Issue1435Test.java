/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1435;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1435")
@WithClasses({
    Config.class,
    Issue1435Mapper.class,
    InObject.class,
    OutObject.class,
})
public class Issue1435Test {
    @Test
    public void mustNotSetListToNull() {
        InObject source = new InObject( "Rainbow Dash" );

        OutObject result = Issue1435Mapper.INSTANCE.map( source );

        assertThat( result.isRainbowDash() ).isTrue();
    }
}
