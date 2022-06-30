/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2018;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2018")
@WithClasses({
    Issue2018Mapper.class,
    Source.class,
    Target.class
})
public class Issue2018Test {

    @ProcessorTest
    public void shouldGenerateCorrectCode() {
        Source source = new Source();
        source.setSomeValue( "value" );

        Target target = Issue2018Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSome_value() ).isEqualTo( "value" );
    }
}
