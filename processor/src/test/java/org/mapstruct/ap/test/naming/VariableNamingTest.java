/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.naming;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for naming of variables/members which conflict with keywords or parameter names.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceTargetMapper.class, While.class, Break.class, Source.class })
@IssueKey("53")
@RunWith(AnnotationProcessorTestRunner.class)
public class VariableNamingTest {

    @Test
    public void shouldGenerateImplementationsOfMethodsWithProblematicVariableNmes() {
        Source source = new Source();

        source.setSomeNumber( 42 );
        source.setValues( Arrays.<Long>asList( 42L, 121L ) );

        Map<Long, Date> map = new HashMap<Long, Date>();
        map.put( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() );
        map.put( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() );
        source.setMap( map );

        Break target = SourceTargetMapper.INSTANCE.sourceToBreak( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).isNotNull();
        assertThat( target.getValues() ).containsOnly( "42", "121" );
        assertThat( target.getSomeNumber() ).isEqualTo( "42" );
        assertThat( target.getMap() ).hasSize( 2 );
        assertThat( target.getMap() ).includes(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );
    }
}
