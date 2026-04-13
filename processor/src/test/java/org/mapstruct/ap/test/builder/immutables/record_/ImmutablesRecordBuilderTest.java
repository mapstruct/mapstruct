/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables.record_;

import org.immutables.value.Value;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests that {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} correctly detects and uses the
 * Immutables-generated {@code {RecordName}Builder} companion class for Java records annotated with
 * {@code @Value.Builder}.
 * <p>
 * The key difference from falling back to the record constructor is that the generated builder enforces
 * mandatory fields: with {@code nullValueCheckStrategy = ALWAYS}, MapStruct skips the builder setter for a
 * {@code null} source value, so {@code build()} throws {@link IllegalStateException} for unset required fields.
 */
@WithClasses({
    Value.class,
    GolfPlayer.class,
    GolfPlayerBuilder.class,
    GolfPlayerDto.class,
    GolfPlayerMapper.class
})
class ImmutablesRecordBuilderTest {

    @ProcessorTest(Compiler.JDK)
    void shouldMapDtoToImmutablesRecord() {
        GolfPlayer player = GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( "Pádraig Harrington", 0, 52 ) );

        assertThat( player.name() ).isEqualTo( "Pádraig Harrington" );
        assertThat( player.handicap() ).isEqualTo( 0 );
        assertThat( player.age() ).isEqualTo( 52 );
    }

    @ProcessorTest(Compiler.JDK)
    void shouldAllowNullForNullableHandicap() {
        GolfPlayer player = GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( "Tiger Woods", null, 48 ) );

        assertThat( player.name() ).isEqualTo( "Tiger Woods" );
        assertThat( player.handicap() ).isNull();
        assertThat( player.age() ).isEqualTo( 48 );
    }

    @ProcessorTest(Compiler.JDK)
    void shouldThrowForNullMandatoryName() {
        // With NullValueCheckStrategy.ALWAYS, MapStruct skips the builder setter for a null source value.
        // The builder's initBits check then fires at build() time because name was never set.
        // Falling back to the record constructor would silently accept null instead.
        assertThrows( IllegalStateException.class,
            () -> GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( null, 0, 48 ) ) );
    }
}
