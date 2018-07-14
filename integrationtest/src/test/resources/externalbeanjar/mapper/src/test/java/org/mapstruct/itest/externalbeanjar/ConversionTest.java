/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.externalbeanjar.Source;
import org.mapstruct.itest.externalbeanjar.Issue1121Mapper;
import org.mapstruct.itest.externalbeanjar.Target;

public class ConversionTest {

    @Test
    public void shouldApplyConversions() {
        Source source = new Source();
        source.setBigDecimal( new BigDecimal( "42" ) );

        Target target = Issue1121Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getInteger() ).isEqualTo( 42 );
    }

}
