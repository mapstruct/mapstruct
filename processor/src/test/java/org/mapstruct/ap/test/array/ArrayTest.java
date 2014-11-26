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
package org.mapstruct.ap.test.array;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.array.source.Scientist;
import org.mapstruct.ap.test.array.target.ScientistDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for array copying
 *
 * @author Sjaak Derksen
 */
@WithClasses( { Scientist.class, ScientistDto.class, ArrayMapper.class, EvaluationMapper.class } )
@RunWith(AnnotationProcessorTestRunner.class)
public class ArrayTest {

    @Test
    @IssueKey("108")
    public void testArrayHandling() throws Exception {

        Scientist scientist = new Scientist( "dr. Jeff" );
        scientist.setEvaluations( new int[]{1, 2, 3} );
        scientist.setUniversities( new String[]{"UCLA", "Princeton"} );

        ScientistDto target = ArrayMapper.INSTANCE.map( scientist );
        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "dr. Jeff" );
        assertThat( target.getEvaluations() ).isEqualTo( new String[]{"1", "2", "3"} );
        assertThat( target.getUniversities() ).isEqualTo( scientist.getUniversities() );
        assertThat( target.getUniversities() ).isNotSameAs( scientist.getUniversities() );

    }

}
