/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.simple;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class
})
class OptionalSimpleTest {

    @ProcessorTest
    @WithClasses(OptionalToOptionalMapper.class)
    void optionalToOptional() {
        Optional<Target> targetOpt = OptionalToOptionalMapper.INSTANCE.map( Optional.empty() );
        assertThat( targetOpt ).isEmpty();

        Source source = new Source();
        source.setValue( "test" );
        targetOpt = OptionalToOptionalMapper.INSTANCE.map( Optional.of( source ) );
        assertThat( targetOpt ).isPresent();
        Target target = targetOpt.get();
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }

    @ProcessorTest
    @WithClasses(OptionalToOptionalNullValueDefaultMapper.class)
    void optionalToOptionalNullValueDefault() {
        Optional<Target> targetOpt = OptionalToOptionalNullValueDefaultMapper.INSTANCE.map( Optional.empty() );
        assertThat( targetOpt ).isPresent();
        Target target = targetOpt.get();
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();

        Source source = new Source();
        source.setValue( "test" );
        targetOpt = OptionalToOptionalNullValueDefaultMapper.INSTANCE.map( Optional.of( source ) );
        assertThat( targetOpt ).isPresent();

        target = targetOpt.get();
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }

    @ProcessorTest
    @WithClasses(OptionalToTypeMapper.class)
    void optionalToType() {
        Target target = OptionalToTypeMapper.INSTANCE.map( Optional.empty() );
        assertThat( target ).isNull();

        Source source = new Source();
        source.setValue( "test" );
        target = OptionalToTypeMapper.INSTANCE.map( Optional.of( source ) );
        assertThat( target.getValue() ).isEqualTo( "test" );
    }

    @ProcessorTest
    @WithClasses(OptionalToTypeNullValueDefaultMapper.class)
    void optionalToTypeNullValueDefault() {
        Target target = OptionalToTypeNullValueDefaultMapper.INSTANCE.map( Optional.empty() );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();

        Source source = new Source();
        source.setValue( "test" );
        target = OptionalToTypeNullValueDefaultMapper.INSTANCE.map( Optional.of( source ) );
        assertThat( target.getValue() ).isEqualTo( "test" );
    }

    @ProcessorTest
    @WithClasses(TypeToOptionalMapper.class)
    void typeToOptional() {
        Optional<Target> targetOpt = TypeToOptionalMapper.INSTANCE.map( null );
        assertThat( targetOpt ).isEmpty();

        Source source = new Source();
        source.setValue( "test" );
        targetOpt = TypeToOptionalMapper.INSTANCE.map( source );
        assertThat( targetOpt ).isNotEmpty();
        Target target = targetOpt.get();
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
