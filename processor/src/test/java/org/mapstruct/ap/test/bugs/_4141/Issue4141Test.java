/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4141;

import java.util.Optional;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * An empty Optional nested source in an update mapping is guarded with a null check instead of a presence check,
 * so the default SET_TO_NULL strategy never clears the target and a null target property gets a new empty instance.
 */
@IssueKey("4141")
@WithClasses(Issue4141Mapper.class)
public class Issue4141Test {

    @ProcessorTest
    void emptyOptionalShouldSetExistingNestedTargetToNull() {
        Issue4141Mapper.Target target = new Issue4141Mapper.Target();
        Issue4141Mapper.NestedTarget existing = new Issue4141Mapper.NestedTarget();
        existing.setName( "existing" );
        target.setNested( existing );

        Issue4141Mapper.INSTANCE.update( new Issue4141Mapper.Source(), target );

        assertThat( target.getNested() ).isNull();
    }

    @ProcessorTest
    void emptyOptionalShouldNotCreateNestedTarget() {
        Issue4141Mapper.Target target = new Issue4141Mapper.Target();

        Issue4141Mapper.INSTANCE.update( new Issue4141Mapper.Source(), target );

        assertThat( target.getNested() ).isNull();
    }

    @ProcessorTest
    void presentOptionalShouldUpdateExistingNestedTarget() {
        Issue4141Mapper.Target target = new Issue4141Mapper.Target();
        Issue4141Mapper.NestedTarget existing = new Issue4141Mapper.NestedTarget();
        existing.setName( "existing" );
        target.setNested( existing );

        Issue4141Mapper.NestedSource nested = new Issue4141Mapper.NestedSource();
        nested.setName( "updated" );
        Issue4141Mapper.Source source = new Issue4141Mapper.Source();
        source.setNested( Optional.of( nested ) );

        Issue4141Mapper.INSTANCE.update( source, target );

        assertThat( target.getNested() ).isSameAs( existing );
        assertThat( target.getNested().getName() ).isEqualTo( "updated" );
    }

    @ProcessorTest
    void nullOptionalShouldKeepSettingNestedTargetToNull() {
        Issue4141Mapper.Target target = new Issue4141Mapper.Target();
        target.setNested( new Issue4141Mapper.NestedTarget() );
        Issue4141Mapper.Source source = new Issue4141Mapper.Source();
        source.setNested( null );

        Issue4141Mapper.INSTANCE.update( source, target );

        assertThat( target.getNested() ).isNull();
    }
}
