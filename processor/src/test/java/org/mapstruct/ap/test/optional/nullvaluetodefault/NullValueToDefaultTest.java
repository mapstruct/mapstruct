/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvaluetodefault;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    NullValueToDefaultMapper.class,
    Source.class,
    Target.class
})
class NullValueToDefaultTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( NullValueToDefaultMapper.class );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
