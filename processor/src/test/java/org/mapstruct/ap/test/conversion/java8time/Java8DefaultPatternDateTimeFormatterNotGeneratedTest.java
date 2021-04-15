/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.java8time;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.conversion.java8time.defaultpatterndatetimeformatternotgenerated.Source;
import org.mapstruct.ap.test.conversion.java8time.defaultpatterndatetimeformatternotgenerated.SourceTargetMapper;
import org.mapstruct.ap.test.conversion.java8time.defaultpatterndatetimeformatternotgenerated.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests that no DateTimeFormatters will be created as mapper instance fields for conversions to/from Java 8 date and
 * time types in case only default patterns are used.
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
@IssueKey( "2329" )
public class Java8DefaultPatternDateTimeFormatterNotGeneratedTest {

    private TimeZone originalTimeZone;

    @Before
    public void setUp() {
        originalTimeZone = TimeZone.getDefault();
    }

    @After
    public void tearDown() {
        TimeZone.setDefault( originalTimeZone );
    }

    @Test
    public void testDateTimeFormattersNotGenerated() {
        Class<? extends SourceTargetMapper> mapperClass = SourceTargetMapper.INSTANCE.getClass();

        // filter by type, $jacocoData might be present as well
        List<Field> declaredFields = Stream
                                           .of( mapperClass.getDeclaredFields() )
                                           .filter( field -> field.getType() == DateTimeFormatter.class )
                                           .collect( Collectors.toList() );
        assertThat( declaredFields )
            .describedAs( "Mapper must not contain any DTF fields as default DateTimeFormatters were applied" )
            .hasSize( 0 );
    }

}
