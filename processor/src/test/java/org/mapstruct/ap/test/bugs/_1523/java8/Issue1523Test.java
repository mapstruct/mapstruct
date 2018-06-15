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
package org.mapstruct.ap.test.bugs._1523.java8;

import java.time.ZonedDateTime;

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
    Issue1523Mapper.class,
    Source.class,
    Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1523")
public class Issue1523Test {

    @Test
    public void testThatCorrectTimeZoneWillBeUsedInTarget() {
        Source source = new Source();
        // we want to test that the timezone will correctly be used in mapped XMLGregorianCalendar and not the
        // default one, so we must ensure that the used timezone is never equal to the default one
        // hopefully the fictive TZ +12:34 is nowhere the default one
        source.setValue( ZonedDateTime.parse( "2018-06-15T00:00:00+12:34" ) );

        Target target = Issue1523Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNotNull();
        assertThat( target.getValue().getTimezone() ).isEqualTo( 12 * 60 + 34 );
    }
}
