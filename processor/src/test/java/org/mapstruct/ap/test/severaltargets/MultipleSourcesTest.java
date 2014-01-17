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
package org.mapstruct.ap.test.severaltargets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import org.mapstruct.ap.testutil.IssueKey;

/**
 * Test for the generation of implementation of abstract base classes.
 *
 * @author Sjaak Derksen
 */
@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, TimeAndFormat.class,
               TimeAndFormatMapper.class })
public class MultipleSourcesTest extends MapperTestBase {

    @Test
    @IssueKey("94")
    public void shouldMapMultipleSources() throws ParseException {
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
