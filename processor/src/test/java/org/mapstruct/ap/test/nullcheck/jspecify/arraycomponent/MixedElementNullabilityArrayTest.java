/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent;

import org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent.MixedElementNullabilityArrayMapper.Source;
import org.mapstruct.ap.test.nullcheck.jspecify.arraycomponent.MixedElementNullabilityArrayMapper.Target;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Two array properties of the same type, which only differ in the nullability of their elements, must not share
 * the same generated mapping method.
 */
@WithJSpecify
@WithClasses( MixedElementNullabilityArrayMapper.class )
class MixedElementNullabilityArrayTest {

    @ProcessorTest
    void shouldNotReuseMethodForDifferentElementNullability() {
        Source source = new Source( new String[] { "1", "2" }, new String[] { "3", null } );

        Target target = MixedElementNullabilityArrayMapper.INSTANCE.map( source );

        assertThat( target.getNonNullElements() ).containsExactly( 1, 2 );
        assertThat( target.getNullableElements() ).containsExactly( 3, null );
    }
}
