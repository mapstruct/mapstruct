/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1566;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    AbstractBuilder.class,
    Issue1566Mapper.class,
    Source.class,
    Target.class
})
@IssueKey("1566")
public class Issue1566Test {

    @ProcessorTest
    public void genericMapperIsCorrectlyUsed() {
        Source source = new Source();
        source.setId( "id-123" );
        source.setName( "Source" );

        Target target = Issue1566Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getId() ).isEqualTo( "id-123" );
        assertThat( target.getName() ).isEqualTo( "Source" );
    }
}
