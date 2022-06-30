/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1966;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sjaak Derksen
 */
@IssueKey("1966")
@WithClasses({
    Issue1966Mapper.class
})
public class Issue1966Test {

    @ProcessorTest
    public void shouldSelectDefaultExpressionEvenWhenSourceInMappingIsNotSpecified() {

        Issue1966Mapper.AnimalRecord dto = new Issue1966Mapper.AnimalRecord();

        Issue1966Mapper.Animal entity = Issue1966Mapper.INSTANCE.toAnimal( dto );

        assertThat( entity.getPreviousNames() ).isNotNull();
        assertThat( entity.getPreviousNames() ).isEmpty();
    }
}
