/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1933;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("1933")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1933Config.class,
    Issue1933Mapper.class
})
public class Issue1933Test {

    @Test
    public void shouldIgnoreIdAndMapUpdateCount() {

        Issue1933Config.Dto dto = new Issue1933Config.Dto();
        dto.id = "id";
        dto.updateCount = 5;

        Issue1933Config.Entity entity = Issue1933Mapper.INSTANCE.map( dto );

        assertThat( entity.id ).isNull();
        assertThat( entity.updateCount ).isEqualTo( 5 );
    }
}
