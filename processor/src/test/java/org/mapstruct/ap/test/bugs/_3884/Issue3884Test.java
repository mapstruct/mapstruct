/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3884;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Test for issue 3884: NullValuePropertyMappingStrategy.SET_TO_DEFAULT should set target Map/Collection to default
 * when source and target are all null.
 */
@IssueKey("3884")
@WithClasses({
    DestinationType.class,
    SourceType.class,
    Issue3884Mapper.class
})
public class Issue3884Test {

    @ProcessorTest
    public void shouldSetTargetToDefaultWhenBothSourceAndTargetAreNull() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        assertThat( source.getAttributes() ).isNull();
        assertThat( target.getAttributes() ).isNull();
        assertThat( source.getItems() ).isNull();
        assertThat( target.getItems() ).isNull();

        Issue3884Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isEmpty();
        assertThat( target.getItems() ).isEmpty();
    }

    @ProcessorTest
    public void shouldClearTargetWhenSourceIsNullAndTargetIsInitialized() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        Map<String, String> targetAttributes = new HashMap<>();
        targetAttributes.put( "targetKey", "targetValue" );
        target.setAttributes( targetAttributes );

        List<String> targetItems = new ArrayList<>();
        targetItems.add( "targetItem" );
        target.setItems( targetItems );

        assertThat( source.getAttributes() ).isNull();
        assertThat( target.getAttributes() ).isNotEmpty();
        assertThat( source.getItems() ).isNull();
        assertThat( target.getItems() ).isNotEmpty();

        Issue3884Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isEmpty();
        assertThat( target.getItems() ).isEmpty();
    }

    @ProcessorTest
    public void shouldCopySourceToTargetWhenSourceIsInitializedAndTargetIsNull() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        source.setAttributes( Map.of( "sourceKey", "sourceValue" ) );
        source.setItems( List.of( "sourceItem" ) );

        assertThat( source.getAttributes() ).isNotEmpty();
        assertThat( target.getAttributes() ).isNull();
        assertThat( source.getItems() ).isNotEmpty();
        assertThat( target.getItems() ).isNull();

        Issue3884Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).containsOnly( entry( "sourceKey", "sourceValue" ) );
        assertThat( target.getItems() ).containsExactly( "sourceItem" );
    }

    @ProcessorTest
    public void shouldCopySourceToTargetWhenBothSourceAndTargetAreInitialized() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        source.setAttributes( Map.of( "sourceKey", "sourceValue" ) );
        source.setItems( List.of( "sourceItem" ) );

        Map<String, String> targetAttributes = new HashMap<>();
        targetAttributes.put( "targetKey", "targetValue" );
        target.setAttributes( targetAttributes );
        List<String> targetItems = new ArrayList<>();
        targetItems.add( "targetItem" );
        target.setItems( targetItems );

        assertThat( source.getAttributes() ).isNotEmpty();
        assertThat( target.getAttributes() ).isNotEmpty();
        assertThat( source.getItems() ).isNotEmpty();
        assertThat( target.getItems() ).isNotEmpty();

        Issue3884Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).containsOnly( entry( "sourceKey", "sourceValue" ) );
        assertThat( target.getItems() ).containsExactly( "sourceItem" );
    }
}
