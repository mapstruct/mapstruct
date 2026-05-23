/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion.Source;
import org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion.SourceTargetMapper;
import org.mapstruct.ap.test.conversion.java8time.zonedoffsetdatetimetolocaldatetimeconversion.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey("4027")
public class ZonedOffsetDateTimeToLocalDateTimeConversionTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource()
            .addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
    public void testZonedDateTimeToLocalDateTimeMapping() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneId.of( "UTC" ) );
        Source source = new Source();
        source.setZonedDateTime( zonedDateTime );
        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        assertThat( target.getZonedDateTime() )
                .isEqualTo( LocalDateTime.of( 2024, 1, 1, 12, 30, 0 ) );
    }

    @ProcessorTest
    public void testOffsetDateTimeToLocalDateTimeMapping() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC );
        Source source = new Source();
        source.setOffsetDateTime( offsetDateTime );
        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        assertThat( target.getOffsetDateTime() )
                .isEqualTo( LocalDateTime.of( 2024, 1, 1, 12, 30, 0 ) );
    }

    @ProcessorTest
    public void testLocalDateTimeToZonedDateTimeMapping() {
        LocalDateTime localDateTime = LocalDateTime.of( 2024, 1, 1, 12, 30, 0 );
        Target target = new Target();
        target.setZonedDateTime( localDateTime );
        Source source = SourceTargetMapper.INSTANCE.toSource( target );
        assertThat( source.getZonedDateTime() )
                .isEqualTo( ZonedDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC ) );
    }

    @ProcessorTest
    public void testLocalDateTimeToOffsetDateTimeMapping() {
        LocalDateTime localDateTime = LocalDateTime.of( 2024, 1, 1, 12, 30, 0 );
        Target target = new Target();
        target.setOffsetDateTime( localDateTime );
        Source source = SourceTargetMapper.INSTANCE.toSource( target );
        assertThat( source.getOffsetDateTime() )
                .isEqualTo( OffsetDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC ) );
    }

    @ProcessorTest
    public void testZonedDateTimeToInstantMapping() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC );
        Source source = new Source();
        source.setZonedDateTimeAsInstant( zonedDateTime );
        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        assertThat( target.getZonedDateTimeAsInstant() )
                .isEqualTo( Instant.parse( "2024-01-01T12:30:00Z" ) );
    }

    @ProcessorTest
    public void testOffsetDateTimeToInstantMapping() {
        OffsetDateTime offsetDateTime = OffsetDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC );
        Source source = new Source();
        source.setOffsetDateTimeAsInstant( offsetDateTime );
        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        assertThat( target.getOffsetDateTimeAsInstant() )
                .isEqualTo( Instant.parse( "2024-01-01T12:30:00Z" ) );
    }

    @ProcessorTest
    public void testInstantToZonedDateTimeMapping() {
        Instant instant = Instant.parse( "2024-01-01T12:30:00Z" );
        Target target = new Target();
        target.setZonedDateTimeAsInstant( instant );
        Source source = SourceTargetMapper.INSTANCE.toSource( target );
        assertThat( source.getZonedDateTimeAsInstant() )
                .isEqualTo( ZonedDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC ) );
    }

    @ProcessorTest
    public void testInstantToOffsetDateTimeMapping() {
        Instant instant = Instant.parse( "2024-01-01T12:30:00Z" );
        Target target = new Target();
        target.setOffsetDateTimeAsInstant( instant );
        Source source = SourceTargetMapper.INSTANCE.toSource( target );
        assertThat( source.getOffsetDateTimeAsInstant() )
                .isEqualTo( OffsetDateTime.of( 2024, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC ) );
    }
}
