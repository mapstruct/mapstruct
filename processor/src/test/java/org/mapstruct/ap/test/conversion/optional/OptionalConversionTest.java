/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithServiceImplementation;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({ OptionalMapper.class, SomeMapper.class, SourceWithOptional.class, TargetWithPlainValues.class })
@IssueKey("674")
@RunWith(AnnotationProcessorTestRunner.class)
@WithServiceImplementation(CustomAccessorNamingStrategy.class)
public class OptionalConversionTest {

    @Test
    public void shouldApplyOptionalConversions() {
        final SourceWithOptional source = new SourceWithOptional();
        source.setOptional( Optional.of( "USD" ) );
        source.setOptionalsSet( Collections.asSet( Optional.of( "EUR" ), Optional.of( "CHF" ) ) );

        final TargetWithPlainValues target = SomeMapper.INSTANCE.asPlain( source );

        assertThat( target ).isNotNull();
        assertThat( target.getOptional() ).isEqualTo( "USD" );
        assertThat( target.getOptionalsSet() )
            .isNotEmpty()
            .containsExactlyInAnyOrder( "EUR", "CHF" );
    }

    @Test
    public void shouldApplyReverseConversions() {
        final TargetWithPlainValues target = new TargetWithPlainValues();
        target.setOptional( "USD" );
        target.setOptionalsSet( Collections.asSet( "JPY" ) );

        final SourceWithOptional source = SomeMapper.INSTANCE.asOptional( target );

        assertThat( source ).isNotNull();
        assertThat( source.getOptional() ).isNotNull();
        assertThat( source.getOptional().isPresent() ).isTrue();
        assertThat( source.getOptional().get() ).isEqualTo( "USD" );
        assertThat( source.getOptionalsSet() ).containsExactlyInAnyOrder( Optional.of( "JPY" ) );
    }

}
