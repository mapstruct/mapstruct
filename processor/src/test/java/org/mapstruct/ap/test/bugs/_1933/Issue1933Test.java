/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1933;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@IssueKey("1933")
@WithClasses({
    Issue1933Config.class,
    Issue1933Mapper.class
})
public class Issue1933Test {

    @ProcessorTest
    public void shouldIgnoreIdAndMapUpdateCount() {

        Issue1933Config.Dto dto = new Issue1933Config.Dto();
        dto.id = "id";
        dto.updateCount = 5;

        Issue1933Config.Entity entity = Issue1933Mapper.INSTANCE.map( dto );

        assertThat( entity.id ).isNull();
        assertThat( entity.updateCount ).isEqualTo( 5 );
    }
}
