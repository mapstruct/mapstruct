/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nullvaluetodefault;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    NullValueToDefaultMapper.class,
    Source.class,
    Target.class })
public class NullValueToDefaultTest {

    @ProcessorTest
    public void constructorOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null ;

        Target target = NullValueToDefaultMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }
    
}
