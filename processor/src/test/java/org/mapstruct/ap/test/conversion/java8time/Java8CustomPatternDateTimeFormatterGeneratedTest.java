/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated.Source;
import org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated.SourceTargetMapper;
import org.mapstruct.ap.test.conversion.java8time.custompatterndatetimeformattergenerated.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Tests generation of DateTimeFormatters as mapper instance fields for conversions to/from Java 8 date and time types.
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey("2329")
public class Java8CustomPatternDateTimeFormatterGeneratedTest {

    @RegisterExtension
    GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( SourceTargetMapper.class );

    @ProcessorTest
    public void testDateTimeFormattersGenerated() {

        Source source = new Source();
        source.setLocalDateTime1( LocalDateTime.of( 2021, Month.MAY, 16, 12, 13, 10 ) );
        source.setLocalDateTime2( LocalDateTime.of( 2020, Month.APRIL, 10, 15, 10, 12 ) );
        source.setLocalDateTime3( LocalDateTime.of( 2021, Month.APRIL, 25, 9, 46, 13 ) );

        Target target = SourceTargetMapper.INSTANCE.map( source );

        assertThat( target.getLocalDateTime1() ).isEqualTo( "16.05.2021 12:13" );
        assertThat( target.getLocalDateTime2() ).isEqualTo( "10.04.2020 15:10" );
        assertThat( target.getLocalDateTime3() ).isEqualTo( "25.04.2021 09.46" );

        source = SourceTargetMapper.INSTANCE.map( target );

        assertThat( source.getLocalDateTime1() ).isEqualTo( LocalDateTime.of( 2021, Month.MAY, 16, 12, 13, 0 ) );
        assertThat( source.getLocalDateTime2() ).isEqualTo( LocalDateTime.of( 2020, Month.APRIL, 10, 15, 10, 0 ) );
        assertThat( source.getLocalDateTime3() ).isEqualTo( LocalDateTime.of( 2021, Month.APRIL, 25, 9, 46, 0 ) );
    }

}
