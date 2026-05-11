/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.qualifier;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for overloaded condition methods with different parameters.
 */
@IssueKey("3993")
@WithClasses({
    OverloadedConditionMapper.class
})
public class OverloadedConditionTest {

    @ProcessorTest
    @WithClasses({
        OverloadedConditionMapper.class
    })
    public void overloadedConditionMethods() {
        OverloadedConditionMapper mapper = OverloadedConditionMapper.INSTANCE;

        // Test mapping without context - should use the condition method without @Context parameter
        OverloadedConditionMapper.Source source = new OverloadedConditionMapper.Source();
        source.setNullable(new OverloadedConditionMapper.JsonNullable<>("test"));

        OverloadedConditionMapper.Target target = mapper.mapWithoutContext(source);
        assertThat(target.getValue()).isEqualTo("test");

        // Test mapping with context - should use the condition method with @Context parameter
        OverloadedConditionMapper.PropertyChangedTracker tracker = new OverloadedConditionMapper.PropertyChangedTracker();
        target = mapper.mapWithContext(source, tracker);
        assertThat(target.getValue()).isEqualTo("test");
        assertThat(tracker.isChanged()).isTrue();
    }
}