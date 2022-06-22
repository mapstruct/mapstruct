/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2530;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Month;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey("2530")
@WithClasses({
    Issue2530Mapper.class
})
public class Issue2530Test {

    @ProcessorTest
    public void shouldConvert() {
        Issue2530Mapper.Test target = Issue2530Mapper.INSTANCE.map( "2021-07-31" );

        assertThat( target ).isNotNull();
        assertThat( target.getDate().getYear() ).isEqualTo( 2021 );
        assertThat( target.getDate().getMonth() ).isEqualTo( Month.JULY );
        assertThat( target.getDate().getDayOfMonth() ).isEqualTo( 31 );
    }
}
