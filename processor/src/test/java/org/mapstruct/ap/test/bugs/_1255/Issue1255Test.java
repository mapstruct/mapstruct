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
package org.mapstruct.ap.test.bugs._1255;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("1255")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    AbstractA.class,
    SomeA.class,
    SomeB.class,
    SomeMapper.class,
    SomeMapperConfig.class})
public class Issue1255Test {

    @Test
    public void shouldMapSomeBToSomeAWithoutField1() throws Exception {
        SomeB someB = new SomeB();
        someB.setField1( "value1" );
        someB.setField2( "value2" );

        SomeA someA = SomeMapper.INSTANCE.toSomeA( someB );

        assertThat( someA.getField1() )
            .isNotEqualTo( someB.getField1() )
            .isNull();
        assertThat( someA.getField2() ).isEqualTo( someB.getField2() );
    }

    @Test
    public void shouldMapSomeAToSomeB() throws Exception {
        SomeA someA = new SomeA();
        someA.setField1( "value1" );
        someA.setField2( "value2" );

        SomeB someB = SomeMapper.INSTANCE.toSomeB( someA );

        assertThat( someB.getField1() ).isEqualTo( someA.getField1() );
        assertThat( someB.getField2() ).isEqualTo( someA.getField2() );
    }
}
