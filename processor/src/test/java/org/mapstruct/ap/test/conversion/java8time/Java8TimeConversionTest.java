/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.conversion.java8time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 *
 */
@RunWith( AnnotationProcessorTestRunner.class )
@WithClasses( { Source.class, Target.class, SourceTargetMapper.class } )
@IssueKey( "121" )
public class Java8TimeConversionTest {

    @Test
    public void testDateTimeToString() {
        Source src = new Source();
        src.setZonedDateTime( ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
    }

    @Test
    public void testLocalDateTimeToString() {
        Source src = new Source();
        src.setLocalDateTime( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
    }

    @Test
    public void testLocalDateToString() {
        Source src = new Source();
        src.setLocalDate( LocalDate.of( 2014, 1, 1 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalDateMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
    }

    @Test
    public void testLocalTimeToString() {
        Source src = new Source();
        src.setLocalTime( LocalTime.of( 0, 0 ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTargetLocalTimeMapped( src );
        assertThat( target ).isNotNull();
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @Test
    public void testSourceToTargetMappingForStrings() {
        Source src = new Source();
        src.setLocalTime( LocalTime.of( 0, 0 ) );
        src.setLocalDate( LocalDate.of( 2014, 1, 1 ) );
        src.setLocalDateTime( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        src.setZonedDateTime( ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) ) );

        // with given format
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( src );

        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );

        // and now with default mappings
        target = SourceTargetMapper.INSTANCE.sourceToTargetDefaultMapping( src );
        assertThat( target ).isNotNull();
        assertThat( target.getZonedDateTime() ).isEqualTo( "01.01.2014 00:00 UTC" );
        assertThat( target.getLocalDateTime() ).isEqualTo( "01.01.2014 00:00" );
        assertThat( target.getLocalDate() ).isEqualTo( "01.01.2014" );
        assertThat( target.getLocalTime() ).isEqualTo( "00:00" );
    }

    @Test
    public void testStringToDateTime() {
        String dateTimeAsString = "01.01.2014 00:00 UTC";
        Target target = new Target();
        target.setZonedDateTime( dateTimeAsString );
        ZonedDateTime sourceDateTime =
                        ZonedDateTime.of( java.time.LocalDateTime.of( 2014, 1, 1, 0, 0 ), ZoneId.of( "UTC" ) );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).isEqualTo( sourceDateTime );
    }

    @Test
    public void testStringToLocalDateTime() {
        String dateTimeAsString = "01.01.2014 00:00";
        Target target = new Target();
        target.setLocalDateTime( dateTimeAsString );
        LocalDateTime sourceDateTime =
                        LocalDateTime.of( 2014, 1, 1, 0, 0, 0 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalDateTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDateTime() ).isEqualTo( sourceDateTime );
    }

    @Test
    public void testStringToLocalDate() {
        String dateTimeAsString = "01.01.2014";
        Target target = new Target();
        target.setLocalDate( dateTimeAsString );
        LocalDate sourceDate =
                        LocalDate.of( 2014, 1, 1 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalDateMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalDate() ).isEqualTo( sourceDate );
    }

    @Test
    public void testStringToLocalTime() {
        String dateTimeAsString = "00:00";
        Target target = new Target();
        target.setLocalTime( dateTimeAsString );
        LocalTime sourceTime =
                        LocalTime.of( 0, 0 );

        Source src = SourceTargetMapper.INSTANCE.targetToSourceLocalTimeMapped( target );
        assertThat( src ).isNotNull();
        assertThat( src.getLocalTime() ).isEqualTo( sourceTime );
    }

    @Test
    public void testTargetToSourceNullMapping() {
        Target target = new Target();
        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src ).isNotNull();
        assertThat( src.getZonedDateTime() ).isNull();
        assertThat( src.getLocalDate() ).isNull();
        assertThat( src.getLocalDateTime() ).isNull();
        assertThat( src.getLocalTime() ).isNull();
    }

    @Test
    public void testTargetToSourceMappingForStrings() {
        Target target = new Target();

        target.setZonedDateTime( "01.01.2014 00:00 UTC" );
        target.setLocalDateTime( "01.01.2014 00:00" );
        target.setLocalDate( "01.01.2014" );
        target.setLocalTime( "00:00" );

        Source src = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( src.getZonedDateTime() ).isEqualTo(
                        ZonedDateTime.of(
                                        java.time.LocalDateTime.of(
                                                        2014,
                                                        1,
                                                        1,
                                                        0,
                                                        0 ), ZoneId.of( "UTC" ) ) );
        assertThat( src.getLocalDateTime() ).isEqualTo( LocalDateTime.of( 2014, 1, 1, 0, 0 ) );
        assertThat( src.getLocalDate() ).isEqualTo( LocalDate.of( 2014, 1, 1 ) );
        assertThat( src.getLocalTime() ).isEqualTo( LocalTime.of( 0, 0 ) );
    }

}
