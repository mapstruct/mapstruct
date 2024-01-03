/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3495;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Oliver Erhart
 */
@IssueKey("3495")
@WithClasses(Issue3495Mapper.class)
class Issue3495Test {

    @ProcessorTest
    void shouldNotClearCollectionBecauseConditionWasNotMet() {

        Issue3495Mapper.Source source = new Issue3495Mapper.Source();

        Issue3495Mapper.TargetWithoutSetter target = new Issue3495Mapper.TargetWithoutSetter();
        target.getNames().add( "name" );

        Issue3495Mapper.INSTANCE.update( source, target );
        assertThat( target ).isNotNull();
        assertThat( target.getNames() ).containsExactly( "name" );
    }
}
