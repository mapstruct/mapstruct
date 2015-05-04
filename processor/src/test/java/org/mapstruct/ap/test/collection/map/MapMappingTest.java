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
package org.mapstruct.ap.test.collection.map;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.collection.map.other.ImportedType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

import static org.fest.assertions.MapAssert.entry;

/**
 * Test for implementation of {@code Map} mapping methods.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceTargetMapper.class, CustomNumberMapper.class, Source.class, Target.class, ImportedType.class })
@IssueKey("44")
@RunWith(AnnotationProcessorTestRunner.class)
public class MapMappingTest {

    @Test
    public void shouldCreateMapMethodImplementation() {
        Map<Long, Date> values = new HashMap<Long, Date>();
        values.put( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() );
        values.put( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() );

        Map<String, String> target = SourceTargetMapper.INSTANCE.longDateMapToStringStringMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).includes(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );
    }

    @Test
    public void shouldCreateReverseMapMethodImplementation() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = SourceTargetMapper.INSTANCE.stringStringMapToLongDateMap( values );

        assertResult( target );
    }

    @Test
    @IssueKey("19")
    public void shouldCreateMapMethodImplementationWithTargetParameter() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = new HashMap<Long, Date>();
        target.put( 66L, new GregorianCalendar( 2013, 7, 16 ).getTime() );

        SourceTargetMapper.INSTANCE.stringStringMapToLongDateMapUsingTargetParameter( target, values );

        assertResult( target );
    }

    @Test
    @IssueKey("19")
    public void shouldCreateMapMethodImplementationWithReturnedTargetParameter() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = new HashMap<Long, Date>();
        target.put( 66L, new GregorianCalendar( 2013, 7, 16 ).getTime() );

        Map<Long, Date> returnedTarget = SourceTargetMapper.INSTANCE
            .stringStringMapToLongDateMapUsingTargetParameterAndReturn( values, target );

        assertThat( target ).isSameAs( returnedTarget );

        assertResult( target );
    }

    private void assertResult(Map<Long, Date> target) {
        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).includes(
            entry( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() ),
            entry( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() )
        );
    }

    private Map<String, String> createStringStringMap() {
        Map<String, String> values = new HashMap<String, String>();
        values.put( "42", "01.01.1980" );
        values.put( "121", "20.07.2013" );
        return values;
    }

    @Test
    public void shouldInvokeMapMethodImplementationForMapTypedProperty() {
        Map<Long, Date> values = new HashMap<Long, Date>();
        values.put( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() );
        values.put( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() );

        Source source = new Source();
        source.setValues( values );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).isNotNull();
        assertThat( target.getValues() ).hasSize( 2 );
        assertThat( target.getValues() ).includes(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );
    }

    @Test
    public void shouldInvokeReverseMapMethodImplementationForMapTypedProperty() {
        Map<String, String> values = createStringStringMap();

        Target target = new Target();
        target.setValues( values );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getValues() ).isNotNull();
        assertThat( source.getValues() ).hasSize( 2 );
        assertThat( source.getValues() ).includes(
            entry( 42L, new GregorianCalendar( 1980, 0, 1 ).getTime() ),
            entry( 121L, new GregorianCalendar( 2013, 6, 20 ).getTime() )
        );
    }

    private Map<Integer, Integer> createIntIntMap() {
        Map<Integer, Integer> values = new HashMap<Integer, Integer>();
        values.put( 42, 47 );
        values.put( 121, 123 );
        return values;
    }

    @Test
    @IssueKey("87")
    public void shouldCreateMapMethodImplementationWithoutConversionOrElementMappingMethod() {
        Map<Integer, Integer> values = createIntIntMap();

        Map<Number, Number> target = SourceTargetMapper.INSTANCE.intIntToNumberNumberMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).isEqualTo( values );
    }
}
