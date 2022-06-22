/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lifecycle;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey( "1433" )
@WithClasses( {
    Item.class,
    ItemDto.class,
    MappingContext.class,
    Order.class,
    OrderDto.class,
    OrderMapper.class
} )
public class BuilderLifecycleCallbacksTest {

    @ProcessorTest
    public void lifecycleMethodsShouldBeInvoked() {
        OrderDto source = new OrderDto();
        source.setCreator( "Filip" );
        ItemDto item1 = new ItemDto();
        item1.setName( "Laptop" );
        ItemDto item2 = new ItemDto();
        item2.setName( "Keyboard" );
        source.setItems( Arrays.asList( item1, item2 ) );
        MappingContext context = new MappingContext();

        OrderMapper.INSTANCE.map( source, context );

        assertThat( context.getInvokedMethods() )
            .contains(
                "beforeWithoutParameters",
                "beforeWithBuilderTargetType",
                "beforeWithBuilderTarget",
                "afterWithoutParameters",
                "afterWithBuilderTargetType",
                "afterWithBuilderTarget",
                "afterWithBuilderTargetReturningTarget"
            );
    }
}
