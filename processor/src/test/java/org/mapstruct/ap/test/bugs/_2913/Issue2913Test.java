/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2913;

import java.math.BigDecimal;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2913")
@WithClasses({
    Issue2913Mapper.class,
})
class Issue2913Test {

    @ProcessorTest
    void shouldNotWidenWithUserDefinedMethods() {
        Issue2913Mapper.Source source = new Issue2913Mapper.Source( BigDecimal.valueOf( 10.543 ) );
        Issue2913Mapper.Target target = Issue2913Mapper.INSTANCE.map( source );

        assertThat( target.getDoubleValue() ).isEqualTo( 10.543 );
        assertThat( target.getDoublePrimitiveValue() ).isEqualTo( 10.543 );
        assertThat( target.getLongValue() ).isEqualTo( 1054 );
        assertThat( target.getLongPrimitiveValue() ).isEqualTo( 1054 );
    }
}
