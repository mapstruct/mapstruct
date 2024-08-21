/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lifecycle;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-21T23:18:51+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order map(OrderDto source, MappingContext context) {
        context.beforeWithoutParameters();
        context.beforeWithSource( source );
        context.beforeWithBuilderTargetType( source, Order.Builder.class );

        context.beforeWithTargetType( source, Order.class );

        if ( source == null ) {
            return null;
        }

        Order.Builder order = Order.builder();

        context.beforeWithBuilderTarget( source, order );

        order.items( itemDtoListToItemList( source.getItems(), context ) );

        context.afterWithoutParameters();
        context.afterWithSource( source );
        context.afterWithBuilderTargetType( source, Order.Builder.class );
        context.afterWithBuilderTarget( source, order );
        Order target = context.afterWithBuilderTargetReturningTarget( order );
        if ( target != null ) {
            return target;
        }

        Order orderResult = order.create();

        context.afterWithTargetType( source, Order.class );
        context.afterWithTarget( source, orderResult );
        Order target1 = context.afterWithTargetReturningTarget( orderResult );
        if ( target1 != null ) {
            return target1;
        }

        return orderResult;
    }

    @Override
    public Item map(ItemDto source, MappingContext context) {
        context.beforeWithoutParameters();

        if ( source == null ) {
            return null;
        }

        Item.Builder item = Item.builder();

        item.name( source.getName() );

        context.afterWithoutParameters();

        return item.create();
    }

    protected List<Item> itemDtoListToItemList(List<ItemDto> list, MappingContext context) {
        context.beforeWithoutParameters();

        if ( list == null ) {
            return null;
        }

        List<Item> list1 = new ArrayList<Item>( list.size() );
        for ( ItemDto itemDto : list ) {
            list1.add( map( itemDto, context ) );
        }

        context.afterWithoutParameters();

        return list1;
    }
}
