/*
 * Copyright 2016 Sjaak Derksen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mapstruct.ap.test.mapperfactories;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey( "751" )
@WithClasses({
    MapperWithSeveralConstructors.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class MapperFactoryTest {

    @Test
    @WithClasses( MapperFactoryWithVarietyOfConstructors.class )
    public void testMapperWithEmptyContructor() {

        // empty
        MapperFactoryWithVarietyOfConstructors mf =
            Mappers.getMapperFactory( MapperFactoryWithVarietyOfConstructors.class );

        MapperWithSeveralConstructors mapper1 = mf.createEmpty();
        assertThat( mapper1.getState1() ).isNull();
        assertThat( mapper1.getState2() ).isNull();

        // with only integer
        MapperWithSeveralConstructors mapper2 = mf.createInteger( 3 );
        assertThat( mapper2.getState1() ).isEqualTo( 3 );
        assertThat( mapper2.getState2() ).isNull();

        // with only integer
        MapperWithSeveralConstructors mapper3 = mf.createString( "test" );
        assertThat( mapper3.getState1() ).isNull();
        assertThat( mapper3.getState2() ).isEqualTo( "test");

        // with only integer, String
        MapperWithSeveralConstructors mapper4 = mf.createIntegerString(3, "test" );
        assertThat( mapper4.getState1() ).isEqualTo( 3 );
        assertThat( mapper4.getState2() ).isEqualTo( "test");

        // with only String, Integer
        MapperWithSeveralConstructors mapper5 = mf.createStringInteger( "test", 3 );
        assertThat( mapper5.getState1() ).isEqualTo( 3 );
        assertThat( mapper5.getState2() ).isEqualTo( "test");

    }

}
