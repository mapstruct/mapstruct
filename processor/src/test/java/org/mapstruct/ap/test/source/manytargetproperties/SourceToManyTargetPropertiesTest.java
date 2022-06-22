/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manytargetproperties;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Test for the generation of implementation of abstract base classes.
 *
 * @author Sjaak Derksen
 */
@WithClasses({
    Source.class, Target.class, SourceTargetMapper.class, TimeAndFormat.class,
    TimeAndFormatMapper.class
})
public class SourceToManyTargetPropertiesTest {

    @ProcessorTest
    @IssueKey("94")
    public void shouldMapSameSourcePropertyToSeveralTargetProperties() {
        Source source = new Source();
        source.setName( "Bob" );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getName1() ).isEqualTo( "Bob" );
        assertThat( target.getName2() ).isEqualTo( "Bob" );
    }

    @ProcessorTest
    @IssueKey("94")
    public void shouldMapSameSourcePropertyToSeveralTargetPropertiesInvokingOtherMapper() throws ParseException {
        Source source = new Source();
        String sourceFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat( sourceFormat );
        Date sourceTime = dateFormat.parse( "09-01-2014" );
        TimeAndFormat sourceTimeAndFormat = new TimeAndFormat();
        sourceTimeAndFormat.setTfFormat( sourceFormat );
        sourceTimeAndFormat.setTfTime( sourceTime );
        source.setTimeAndFormat( sourceTimeAndFormat );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFormat() ).isEqualTo( sourceFormat );
        assertThat( target.getTime() ).isEqualTo( sourceTime );
    }
}
