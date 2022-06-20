/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1799;

import java.util.Date;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue1799Mapper.class,
    Source.class,
    Target.class,
})
@IssueKey("1799")
public class Issue1799Test {

    @ProcessorTest
    public void fluentJavaBeanStyleSettersShouldWork() {
        Target target = Issue1799Mapper.INSTANCE.map( new Source( new Date( 150 ), "Switzerland" ) );

        assertThat( target.getSettlementDate() ).isEqualTo( new Date( 150 ) );
        assertThat( target.getGetawayLocation() ).isEqualTo( "Switzerland" );
    }
}
