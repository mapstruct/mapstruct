/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests conversions between {@link Optional} and {@link Object}.
 *
 * @author Iaroslav Bogdanchikov
 */
@WithClasses({
    SourceTargetMapper.class,
    Source.class,
    Target.class
})
public class OptionalConversionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
    public void shouldApplyOptionalConversions() {
        Source source = new Source();
        source.setOptional( Optional.of( "TEST" ) );
        source.setFromOptionalProp( Optional.of( "FROM OPTIONAL TEST" ) );
        source.setToOptionalProp( "TO OPTIONAL TEST" );
        source.fromOptionalPubProp = Optional.of( "FROM OPTIONAL PUBLIC TEST" );
        source.toOptionalPubProp = "TO OPTIONAL PUBLIC TEST";

        Target target = SourceTargetMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getOptional() ).isPresent();
        assertThat( target.getOptional() ).get().isEqualTo( "TEST" );
        assertThat( target.getFromOptionalProp() ).isEqualTo( "FROM OPTIONAL TEST" );
        assertThat( target.getToOptionalProp() ).isPresent();
        assertThat( target.getToOptionalProp() ).get().isEqualTo( "TO OPTIONAL TEST" );
        assertThat( target.fromOptionalPubProp ).isEqualTo( "FROM OPTIONAL PUBLIC TEST" );
        assertThat( target.toOptionalPubProp ).isPresent();
        assertThat( target.toOptionalPubProp ).get().isEqualTo( "TO OPTIONAL PUBLIC TEST" );
    }

    @ProcessorTest
    public void shouldApplyOptionalConversionsWhenNullOrEmpty() {
        Source source = new Source();
        source.setOptional( Optional.empty() );
        source.setFromOptionalProp( Optional.empty() );
        source.setToOptionalProp( null );
        source.fromOptionalPubProp = Optional.empty();
        source.toOptionalPubProp = null;

        Target target = SourceTargetMapper.INSTANCE.map( source );
        assertThat( target ).isNotNull();
        assertThat( target.getOptional() ).isEmpty();
        assertThat( target.getFromOptionalProp() ).isNull();
        assertThat( target.getToOptionalProp() ).isEmpty();
        assertThat( target.fromOptionalPubProp ).isNull();
        assertThat( target.toOptionalPubProp ).isEmpty();
    }
}
