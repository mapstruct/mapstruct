/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2164;

import java.math.BigDecimal;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2164")
@WithClasses(Issue2164Mapper.class)
public class Issue2164Test {

    @ProcessorTest
    public void shouldSelectProperMethod() {

        Issue2164Mapper.Target target = Issue2164Mapper.INSTANCE.map( new BigDecimal( "1234" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "12" );
    }
}
