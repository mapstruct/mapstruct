/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3865;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for issue 3865: NullValuePropertyMappingStrategy.SET_TO_DEFAULT should set target Map/Collection to default
 * when source and target are all null.
 */
@IssueKey("3865")
@WithClasses({
    DestinationType.class,
    SourceType.class,
    Issue3865Mapper.class
})
public class Issue3865Test {

    @ProcessorTest
    public void shouldSetTargetToDefaultWhenBothSourceAndTargetAreNull() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        assertThat( source.getAttributes() ).isNull();
        assertThat( target.getAttributes() ).isNull();
        assertThat( source.getItems() ).isNull();
        assertThat( target.getItems() ).isNull();

        Issue3865Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).isEmpty();
        assertThat( target.getItems() ).isNotNull();
        assertThat( target.getItems() ).isEmpty();

        assertThat( target.getInitializedAttributes() ).containsKey( "key1" );
        assertThat( target.getInitializedItems() ).contains( "item1" );
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
        assertThat( target.getAttributes() ).isNotNull();
        assertThat( source.getItems() ).isNull();
        assertThat( target.getItems() ).isNotNull();

        Issue3865Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).isEmpty();
        assertThat( target.getItems() ).isNotNull();
        assertThat( target.getItems() ).isEmpty();
    }

    @ProcessorTest
    public void shouldCopySourceToTargetWhenSourceIsInitializedAndTargetIsNull() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        Map<String, String> sourceAttributes = new HashMap<>();
        sourceAttributes.put( "sourceKey", "sourceValue" );
        source.setAttributes( sourceAttributes );

        List<String> sourceItems = new ArrayList<>();
        sourceItems.add( "sourceItem" );
        source.setItems( sourceItems );

        assertThat( source.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).isNull();
        assertThat( source.getItems() ).isNotNull();
        assertThat( target.getItems() ).isNull();

        Issue3865Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).containsEntry( "sourceKey", "sourceValue" );
        assertThat( target.getItems() ).isNotNull();
        assertThat( target.getItems() ).contains( "sourceItem" );
    }

    @ProcessorTest
    public void shouldCopySourceToTargetWhenBothSourceAndTargetAreInitialized() {
        DestinationType target = new SourceType();
        SourceType source = new SourceType();

        Map<String, String> sourceAttributes = new HashMap<>();
        sourceAttributes.put( "sourceKey", "sourceValue" );
        source.setAttributes( sourceAttributes );

        Map<String, String> targetAttributes = new HashMap<>();
        targetAttributes.put( "targetKey", "targetValue" );
        target.setAttributes( targetAttributes );

        List<String> sourceItems = new ArrayList<>();
        sourceItems.add( "sourceItem" );
        source.setItems( sourceItems );

        List<String> targetItems = new ArrayList<>();
        targetItems.add( "targetItem" );
        target.setItems( targetItems );

        assertThat( source.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).isNotNull();
        assertThat( source.getItems() ).isNotNull();
        assertThat( target.getItems() ).isNotNull();

        Issue3865Mapper.INSTANCE.update( target, source );

        assertThat( target.getAttributes() ).isNotNull();
        assertThat( target.getAttributes() ).containsEntry( "sourceKey", "sourceValue" );
        assertThat( target.getAttributes() ).doesNotContainKey( "targetKey" );

        assertThat( target.getItems() ).isNotNull();
        assertThat( target.getItems() ).contains( "sourceItem" );
        assertThat( target.getItems() ).doesNotContain( "targetItem" );
    }
}
