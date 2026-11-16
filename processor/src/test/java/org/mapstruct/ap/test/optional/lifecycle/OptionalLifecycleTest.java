/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.lifecycle;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    MappingContext.class,
    Source.class,
    Target.class,
})
public class OptionalLifecycleTest {

    @ProcessorTest
    @WithClasses(OptionalToOptionalMapper.class)
    void optionalToOptional() {
        MappingContext context = new MappingContext();

        OptionalToOptionalMapper.INSTANCE.map( Optional.empty(), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType"
            );

        context = new MappingContext();
        OptionalToOptionalMapper.INSTANCE.map( Optional.of( new Source() ), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithTarget",
                "afterWithoutParameters",
                "afterWithOptionalSource",
                "afterWithTargetType",
                "afterWithTarget",
                "afterWithOptionalTarget"
            );
    }

    @ProcessorTest
    @WithClasses(OptionalToOptionalWithBuilderMapper.class)
    void optionalToOptionalWithBuilder() {
        MappingContext context = new MappingContext();

        OptionalToOptionalWithBuilderMapper.INSTANCE.map( Optional.empty(), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithBuilderTargetType"
            );

        context = new MappingContext();
        OptionalToOptionalWithBuilderMapper.INSTANCE.map( Optional.of( new Source() ), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithBuilderTargetType",
                "beforeWithTargetBuilder",
                "afterWithoutParameters",
                "afterWithOptionalSource",
                "afterWithTargetType",
                "afterWithBuilderTargetType",
                "afterWithTarget",
                "afterWithTargetBuilder",
                "afterWithOptionalTarget"
            );
    }

    @ProcessorTest
    @WithClasses(OptionalToTypeMapper.class)
    void optionalToType() {
        MappingContext context = new MappingContext();

        OptionalToTypeMapper.INSTANCE.map( Optional.empty(), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType"
            );

        context = new MappingContext();
        OptionalToTypeMapper.INSTANCE.map( Optional.of( new Source() ), context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithTarget",
                "afterWithoutParameters",
                "afterWithOptionalSource",
                "afterWithTargetType",
                "afterWithTarget"
            );
    }

    @ProcessorTest
    @WithClasses(OptionalToTypeMultiSourceMapper.class)
    void optionalToTypeMultiSource() {
        MappingContext context = new MappingContext();

        OptionalToTypeMultiSourceMapper.INSTANCE.map( Optional.empty(), null, context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType"
            );

        context = new MappingContext();
        OptionalToTypeMultiSourceMapper.INSTANCE.map( Optional.of( new Source() ), null, context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithTarget",
                "afterWithoutParameters",
                "afterWithOptionalSource",
                "afterWithTargetType",
                "afterWithTarget"
            );

        context = new MappingContext();
        OptionalToTypeMultiSourceMapper.INSTANCE.map( Optional.empty(), "Test", context );

        assertThat( context.getInvokedMethods() )
            .containsExactlyInAnyOrder(
                "beforeWithoutParameters",
                "beforeWithOptionalSource",
                "beforeWithTargetType",
                "beforeWithTarget",
                "afterWithoutParameters",
                "afterWithOptionalSource",
                "afterWithTargetType",
                "afterWithTarget"
            );
    }
}
