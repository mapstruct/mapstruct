/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * @author Andreas Gudian
 */
public abstract class BaseMapper {

    public abstract Target sourceToTarget(Source source);

    @Qualified
    public abstract Target sourceToTargetQualified(Source source);

    private static final List<Invocation> INVOCATIONS = new ArrayList<>();

    @BeforeMapping
    public void noArgsBeforeMapping() {
        INVOCATIONS.add( new Invocation( "noArgsBeforeMapping" ) );
    }

    @BeforeMapping
    public void withSourceBeforeMapping(Source source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMapping", source ) );
    }

    @BeforeMapping
    @Qualified
    public void withSourceBeforeMappingQualified(Source source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMappingQualified", source ) );
    }

    @BeforeMapping
    public void withSourceBeforeMapping(SourceEnum source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMapping", source ) );
    }

    @BeforeMapping
    public void withSourceBeforeMapping(List<Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMapping", source ) );
    }

    @BeforeMapping
    @Qualified
    public void withSourceBeforeMappingQualified(List<Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMappingQualified", source ) );
    }

    @BeforeMapping
    public void withSourceBeforeMapping(Map<String, Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMapping", source ) );
    }

    @BeforeMapping
    @Qualified
    public void withSourceBeforeMappingQualified(Map<String, Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceBeforeMappingQualified", source ) );
    }

    @BeforeMapping
    public void withSourceAsObjectBeforeMapping(Object source) {
        INVOCATIONS.add( new Invocation( "withSourceAsObjectBeforeMapping", source ) );
    }

    @BeforeMapping
    public <T> void withSourceAndTargetTypeBeforeMapping(Source source, @TargetType Class<T> targetClass) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetTypeBeforeMapping", source, targetClass ) );
    }

    @BeforeMapping
    public <T> void withSourceAndTargetTypeBeforeMapping(SourceEnum source, @TargetType Class<T> targetClass) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetTypeBeforeMapping", source, targetClass ) );
    }

    @BeforeMapping
    public <T> void withSourceAndTargetTypeBeforeMapping(List<Source> source, @TargetType Class<T> targetClass) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetTypeBeforeMapping", source, targetClass ) );
    }

    @BeforeMapping
    public <T> void withSourceAndTargetTypeBeforeMapping(Map<String, Source> source, @TargetType Class<T> targetClass) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetTypeBeforeMapping", source, targetClass ) );
    }

    @BeforeMapping
    public void withSourceAndTargetBeforeMapping(Source source, @MappingTarget Target target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetBeforeMapping", source, target ) );
    }

    @BeforeMapping
    public void withSourceAndTargetBeforeMapping(SourceEnum source, @MappingTarget TargetEnum target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetBeforeMapping", source, target ) );
    }

    @BeforeMapping
    public void withSourceAndTargetBeforeMapping(List<Source> source, @MappingTarget List<Target> target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetBeforeMapping", source, target ) );
    }

    @BeforeMapping
    public void withSourceAndTargetBeforeMapping(Map<String, Source> source,
                                                 @MappingTarget Map<String, Target> target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetBeforeMapping", source, target ) );
    }

    @BeforeMapping
    public void withTargetBeforeMapping(@MappingTarget Target target) {
        INVOCATIONS.add( new Invocation( "withTargetBeforeMapping", target ) );
    }

    @BeforeMapping
    public void withTargetBeforeMapping(@MappingTarget TargetEnum target) {
        INVOCATIONS.add( new Invocation( "withTargetBeforeMapping", target ) );
    }

    @BeforeMapping
    public void withTargetBeforeMapping(@MappingTarget List<Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetBeforeMapping", target ) );
    }

    @BeforeMapping
    public void withTargetBeforeMapping(@MappingTarget Map<String, Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetBeforeMapping", target ) );
    }

    @BeforeMapping
    public void withTargetAsObjectBeforeMapping(@MappingTarget Object target) {
        INVOCATIONS.add( new Invocation( "withTargetAsObjectBeforeMapping", target ) );
    }

    @AfterMapping
    public void noArgsAfterMapping() {
        INVOCATIONS.add( new Invocation( "noArgsAfterMapping" ) );
    }

    @AfterMapping
    public void withSourceAfterMapping(Source source) {
        INVOCATIONS.add( new Invocation( "withSourceAfterMapping", source ) );
    }

    @AfterMapping
    public void withSourceAfterMapping(SourceEnum source) {
        INVOCATIONS.add( new Invocation( "withSourceAfterMapping", source ) );
    }

    @AfterMapping
    public void withSourceAfterMapping(List<Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceAfterMapping", source ) );
    }

    @AfterMapping
    public void withSourceAfterMapping(Map<String, Source> source) {
        INVOCATIONS.add( new Invocation( "withSourceAfterMapping", source ) );
    }

    @AfterMapping
    public void withSourceAsObjectAfterMapping(Object source) {
        INVOCATIONS.add( new Invocation( "withSourceAsObjectAfterMapping", source ) );
    }

    @AfterMapping
    public void withSourceAndTargetAfterMapping(Source source, @MappingTarget Target target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetAfterMapping", source, target ) );
    }

    @AfterMapping
    public void withSourceAndTargetAfterMapping(SourceEnum source, @MappingTarget TargetEnum target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetAfterMapping", source, target ) );
    }

    @AfterMapping
    public void withSourceAndTargetAfterMapping(List<Source> source, @MappingTarget List<Target> target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetAfterMapping", source, target ) );
    }

    @AfterMapping
    public void withSourceAndTargetAfterMapping(Map<String, Source> source, @MappingTarget Map<String, Target> target) {
        INVOCATIONS.add( new Invocation( "withSourceAndTargetAfterMapping", source, target ) );
    }

    @AfterMapping
    public void withTargetAfterMapping(@MappingTarget Target target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMapping", target ) );
    }

    @AfterMapping
    public void withTargetAfterMapping(@MappingTarget TargetEnum target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMapping", target ) );
    }

    @AfterMapping
    @Qualified
    public void withTargetAfterMappingQualified(@MappingTarget Target target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMappingQualified", target ) );
    }

    @AfterMapping
    public void withTargetAfterMapping(@MappingTarget List<Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMapping", target ) );
    }

    @AfterMapping
    @Qualified
    public void withTargetAfterMappingQualified(@MappingTarget List<Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMappingQualified", target ) );
    }

    @AfterMapping
    public void withTargetAfterMapping(@MappingTarget Map<String, Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMapping", target ) );
    }

    @AfterMapping
    @Qualified
    public void withTargetAfterMappingQualified(@MappingTarget Map<String, Target> target) {
        INVOCATIONS.add( new Invocation( "withTargetAfterMappingQualified", target ) );
    }

    @AfterMapping
    public void withTargetAsObjectAfterMapping(@MappingTarget Object target) {
        INVOCATIONS.add( new Invocation( "withTargetAsObjectAfterMapping", target ) );
    }

    @AfterMapping
    public <T> void withTargetAndTargetTypeAfterMapping(@MappingTarget T target, @TargetType Class<T> targetClass) {
        INVOCATIONS.add( new Invocation( "withTargetAndTargetTypeAfterMapping", target, targetClass ) );
    }

    public static List<Invocation> getInvocations() {
        return INVOCATIONS;
    }

    public static void reset() {
        INVOCATIONS.clear();
    }
}
