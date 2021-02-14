/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1714;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1714")
@WithClasses({
    Issue1714Mapper.class
})
public class Issue1714Test {

    @ProcessorTest
    public void codeShouldBeGeneratedCorrectly() {

        Issue1714Mapper.OnDemand source = new Issue1714Mapper.OnDemand();
        source.setProgramInstance( new Issue1714Mapper.Program() );

        Issue1714Mapper.OfferEntity result = Issue1714Mapper.INSTANCE.map( source );

        assertThat( result.getSeasonNumber() ).isEqualTo( "1" );

    }
}
