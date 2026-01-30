/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.lifecycle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Filip Hrisafov
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class MappingContext {

    private final List<String> invokedMethods = new ArrayList<>();

    @BeforeMapping
    public void beforeWithoutParameters() {
        invokedMethods.add( "beforeWithoutParameters" );
    }

    @BeforeMapping
    public void beforeWithOptionalSource(Optional<Source> source) {
        invokedMethods.add( "beforeWithOptionalSource" );
    }

    @BeforeMapping
    public void beforeWithSource(Source source) {
        invokedMethods.add( "beforeWithSource" );
    }

    @BeforeMapping
    public void beforeWithTargetType(@TargetType Class<Target> targetClass) {
        invokedMethods.add( "beforeWithTargetType" );
    }

    @BeforeMapping
    public void beforeWithBuilderTargetType(@TargetType Class<Target.Builder> targetBuilderClass) {
        invokedMethods.add( "beforeWithBuilderTargetType" );
    }

    @BeforeMapping
    public void beforeWithOptionalTarget(@MappingTarget Optional<Target> target) {
        invokedMethods.add( "beforeWithOptionalTarget" );
    }

    @BeforeMapping
    public void beforeWithTarget(@MappingTarget Target target) {
        invokedMethods.add( "beforeWithTarget" );
    }

    @BeforeMapping
    public void beforeWithTargetBuilder(@MappingTarget Target.Builder target) {
        invokedMethods.add( "beforeWithTargetBuilder" );
    }

    @AfterMapping
    public void afterWithoutParameters() {
        invokedMethods.add( "afterWithoutParameters" );
    }

    @AfterMapping
    public void afterWithOptionalSource(Optional<Source> source) {
        invokedMethods.add( "afterWithOptionalSource" );
    }

    @AfterMapping
    public void afterWithSource(Source source) {
        invokedMethods.add( "afterWithSource" );
    }

    @AfterMapping
    public void afterWithTargetType(@TargetType Class<Target> targetClass) {
        invokedMethods.add( "afterWithTargetType" );
    }

    @AfterMapping
    public void afterWithBuilderTargetType(@TargetType Class<Target.Builder> targetClass) {
        invokedMethods.add( "afterWithBuilderTargetType" );
    }

    @AfterMapping
    public void afterWithTarget(@MappingTarget Target target) {
        invokedMethods.add( "afterWithTarget" );
    }

    @AfterMapping
    public void afterWithTargetBuilder(@MappingTarget Target.Builder target) {
        invokedMethods.add( "afterWithTargetBuilder" );
    }

    @AfterMapping
    public void afterWithOptionalTarget(@MappingTarget Optional<Target> target) {
        invokedMethods.add( "afterWithOptionalTarget" );
    }

    public List<String> getInvokedMethods() {
        return invokedMethods;
    }
}
