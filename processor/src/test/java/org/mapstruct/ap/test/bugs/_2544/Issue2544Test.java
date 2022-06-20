/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2544;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junitpioneer.jupiter.DefaultLocale;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2544" )
@WithClasses( { Issue2544Mapper.class } )
public class Issue2544Test {
    // Parsing numbers is sensitive to locale settings (e.g. decimal point)

    @ProcessorTest
    @DefaultLocale("en")
    public void shouldConvertEn() {
        Issue2544Mapper.Target target = Issue2544Mapper.INSTANCE.map( "123.45679E6" );

        assertThat( target ).isNotNull();
        assertThat( target.getBigNumber() ).isEqualTo( new BigDecimal( "1.2345679E+8" ) );
    }

    @ProcessorTest
    @DefaultLocale("de")
    public void shouldConvertDe() {
        Issue2544Mapper.Target target = Issue2544Mapper.INSTANCE.map( "123,45679E6" );

        assertThat( target ).isNotNull();
        assertThat( target.getBigNumber() ).isEqualTo( new BigDecimal( "1.2345679E+8" ) );
    }
}
