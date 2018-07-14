/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._855;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses({ OrderedSource.class, OrderedTarget.class, OrderDemoMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class OrderingBug855Test {

    @Test
    @IssueKey("855")
    public void shouldApplyCorrectOrderingWithDependsOn() {
        OrderedSource source = new OrderedSource();

        OrderedTarget target = OrderDemoMapper.INSTANCE.orderedWithDependsOn( source );

        assertThat( target.getOrder() ).containsExactly( "field2", "field0", "field1", "field3", "field4" );
    }

    @Test
    public void shouldRetainDefaultOrderWithoutDependsOn() {
        OrderedSource source = new OrderedSource();

        OrderedTarget target = OrderDemoMapper.INSTANCE.orderedWithoutDependsOn( source );

        assertThat( target.getOrder() ).containsExactly( "field0", "field1", "field2", "field3", "field4" );
    }
}
