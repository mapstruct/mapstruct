/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.bugs._1460;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@WithClasses({
    Issue1460Mapper.class,
    Source.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1460")
public class Issue1460Test {

    @Test
    public void shouldTestMappingLocalDates() {
        long dateInMs = 1524693600000L;
        String dateAsString = "2018-04-26";
        String dateTimeAsString = dateAsString + "T00:00:00+00:00";

        Source source = new Source();
        source.setStringToEnum( "OK" );
        source.setDateToJodaDateTime( new Date( dateInMs ) );
        source.setJodaDateTimeToCalendar( DateTime.parse( dateTimeAsString ) );

        Target target = Issue1460Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringToEnum() ).isEqualTo( Target.Issue1460Enum.OK );
        assertThat( target.getDateToJodaDateTime() ).isEqualTo(
            new DateTime( new Date( dateInMs ) )
        );
        assertThat( target.getJodaDateTimeToCalendar().getTimeInMillis() ).isEqualTo(
            DateTime.parse( dateTimeAsString ).toCalendar( Locale.getDefault() ).getTimeInMillis()
        );
    }
}
