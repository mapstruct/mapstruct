/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

    private final List<String> invokedMethods = new ArrayList<String>();

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

        return orderBuilder.create();
    }

    public List<String> getInvokedMethods() {
        return invokedMethods;
    }
}
