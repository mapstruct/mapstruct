/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2544;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2544" )
@WithClasses( { Issue2544Mapper.class } )
public class Issue2544Test {

    @ProcessorTest
    public void shouldConvert() {
        Issue2544Mapper.Target target = Issue2544Mapper.INSTANCE.map( "123.45679E6" );

        assertThat( target ).isNotNull();
        assertThat( target.getBigNumber() ).isEqualTo( new BigDecimal( "1.2345679E+8" ) );
    }
}
