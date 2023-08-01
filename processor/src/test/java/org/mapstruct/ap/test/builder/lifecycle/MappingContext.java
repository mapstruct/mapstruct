/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.lifecycle;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Filip Hrisafov
 */
public class MappingContext {

    private final List<String> invokedMethods = new ArrayList<>();

    @BeforeMapping
    public void beforeWithoutParameters() {
        invokedMethods.add( "beforeWithoutParameters" );
    }

    @BeforeMapping
    public void beforeWithTargetType(OrderDto source, @TargetType Class<Order> orderClass) {
        invokedMethods.add( "beforeWithTargetType" );
    }

    @BeforeMapping
    public void beforeWithBuilderTargetType(OrderDto source, @TargetType Class<Order.Builder> builderClass) {
        invokedMethods.add( "beforeWithBuilderTargetType" );
    }

    @BeforeMapping
    public void beforeWithTarget(OrderDto source, @MappingTarget Order order) {
        invokedMethods.add( "beforeWithTarget" );
    }

    @BeforeMapping
    public void beforeWithBuilderTarget(OrderDto source, @MappingTarget Order.Builder orderBuilder) {
        invokedMethods.add( "beforeWithBuilderTarget" );
    }

    @AfterMapping
    public void afterWithoutParameters() {
        invokedMethods.add( "afterWithoutParameters" );
    }

    @AfterMapping
    public void afterWithTargetType(OrderDto source, @TargetType Class<Order> orderClass) {
        invokedMethods.add( "afterWithTargetType" );
    }

    @AfterMapping
    public void afterWithBuilderTargetType(OrderDto source, @TargetType Class<Order.Builder> builderClass) {
        invokedMethods.add( "afterWithBuilderTargetType" );
    }

    @AfterMapping
    public void afterWithTarget(OrderDto source, @MappingTarget Order order) {
        invokedMethods.add( "afterWithTarget" );
    }

    @AfterMapping
    public void afterWithBuilderTarget(OrderDto source, @MappingTarget Order.Builder orderBuilder) {
        invokedMethods.add( "afterWithBuilderTarget" );
    }

    @AfterMapping
    public Order afterWithBuilderTargetReturningTarget(@MappingTarget Order.Builder orderBuilder) {
        invokedMethods.add( "afterWithBuilderTargetReturningTarget" );

        // return null, so that @AfterMapping methods on the finalized object will be called in the tests
        return null;
    }

    @AfterMapping
    public Order afterWithTargetReturningTarget(@MappingTarget Order order) {
        invokedMethods.add( "afterWithTargetReturningTarget" );

        return order;
    }

    public List<String> getInvokedMethods() {
        return invokedMethods;
    }
}
