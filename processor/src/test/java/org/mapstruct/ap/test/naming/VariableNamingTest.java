/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.naming;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junitpioneer.jupiter.ReadsDefaultTimeZone;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Test for naming of variables/members which conflict with keywords or parameter names.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceTargetMapper.class, While.class, Break.class, Source.class })
@IssueKey("53")
@ReadsDefaultTimeZone
public class VariableNamingTest {

    @ProcessorTest
    public void shouldGenerateImplementationsOfMethodsWithProblematicVariableNmes() {
        Source source = new Source();

        source.setSomeNumber( 42 );
        source.setValues( Arrays.asList( 42L, 121L ) );

        Map<Long, Date> map = new HashMap<>();
        map.put( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() );
        map.put( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() );
        source.setMap( map );

        Break target = SourceTargetMapper.INSTANCE.sourceToBreak( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).isNotNull();
        assertThat( target.getValues() ).containsOnly( "42", "121" );
        assertThat( target.getSomeNumber() ).isEqualTo( "42" );
        assertThat( target.getMap() ).hasSize( 2 );
        assertThat( target.getMap() ).contains(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );
    }
}
