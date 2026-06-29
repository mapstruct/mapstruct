/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.record_;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test verifying that {@link org.mapstruct.ap.spi.ImmutablesBuilderProvider} correctly detects
 * the Immutables-generated {@code GolfPlayerBuilder} for a Java record annotated with {@code @Value.Builder}.
 * <p>
 * The mapper uses {@code nullValueCheckStrategy = ALWAYS} so MapStruct skips the builder setter for {@code null}
 * source values. This means a mandatory field left unset causes the builder to throw
 * {@link IllegalStateException} at {@code build()} time — behaviour that would be silently lost if MapStruct
 * fell back to calling the record constructor directly.
 */
public class ImmutablesRecordBuilderTest {

    @Test
    public void shouldMapDtoToImmutablesRecord() {
        GolfPlayer player = GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( "Pádraig Harrington", 0, 52 ) );

        assertThat( player.name() ).isEqualTo( "Pádraig Harrington" );
        assertThat( player.handicap() ).isEqualTo( 0 );
        assertThat( player.age() ).isEqualTo( 52 );
    }

    @Test
    public void shouldAllowNullForNullableHandicap() {
        // handicap is @Nullable so the builder accepts null for it
        GolfPlayer player = GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( "Tiger Woods", null, 48 ) );

        assertThat( player.name() ).isEqualTo( "Tiger Woods" );
        assertThat( player.handicap() ).isNull();
        assertThat( player.age() ).isEqualTo( 48 );
    }

    @Test( expected = IllegalStateException.class )
    public void shouldRejectNullForMandatoryName() {
        // name has no @Nullable: MapStruct skips the builder setter when the source value is null,
        // so build() throws IllegalStateException for the unset required field.
        // Falling back to the record constructor would silently accept null instead.
        GolfPlayerMapper.INSTANCE.toGolfPlayer( new GolfPlayerDto( null, 0, 48 ) );
    }
}