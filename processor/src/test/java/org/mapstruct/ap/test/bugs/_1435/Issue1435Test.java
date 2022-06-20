/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1435;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("1435")
@WithClasses({
    Config.class,
    Issue1435Mapper.class,
    InObject.class,
    OutObject.class,
})
public class Issue1435Test {
    @ProcessorTest
    public void mustNotSetListToNull() {
        InObject source = new InObject( "Rainbow Dash" );

        OutObject result = Issue1435Mapper.INSTANCE.map( source );

        assertThat( result.isRainbowDash() ).isTrue();
    }
}
