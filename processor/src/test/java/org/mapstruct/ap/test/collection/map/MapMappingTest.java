/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junitpioneer.jupiter.DefaultTimeZone;
import org.mapstruct.ap.test.collection.map.other.ImportedType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

/**
 * Test for implementation of {@code Map} mapping methods.
 *
 * @author Gunnar Morling
 */
@WithClasses({ SourceTargetMapper.class, CustomNumberMapper.class, Source.class, Target.class, ImportedType.class })
@IssueKey("44")
@DefaultTimeZone("UTC")
public class MapMappingTest {

    @ProcessorTest
    public void shouldCreateMapMethodImplementation() {
        Map<Long, Date> values = new HashMap<>();
        values.put( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() );
        values.put( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() );

        Map<String, String> target = SourceTargetMapper.INSTANCE.longDateMapToStringStringMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).contains(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );
    }

    @ProcessorTest
    public void shouldCreateReverseMapMethodImplementation() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = SourceTargetMapper.INSTANCE.stringStringMapToLongDateMap( values );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldCreateMapMethodImplementationWithTargetParameter() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = new HashMap<>();
        target.put( 66L, new GregorianCalendar( 2013, Calendar.AUGUST, 16 ).getTime() );

        SourceTargetMapper.INSTANCE.stringStringMapToLongDateMapUsingTargetParameter( target, values );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("19")
    public void shouldCreateMapMethodImplementationWithReturnedTargetParameter() {
        Map<String, String> values = createStringStringMap();

        Map<Long, Date> target = new HashMap<>();
        target.put( 66L, new GregorianCalendar( 2013, Calendar.AUGUST, 16 ).getTime() );

        Map<Long, Date> returnedTarget = SourceTargetMapper.INSTANCE
            .stringStringMapToLongDateMapUsingTargetParameterAndReturn( values, target );

        assertThat( target ).isSameAs( returnedTarget );

        assertResult( target );
    }

    @ProcessorTest
    @IssueKey("1752")
    public void shouldCreateMapMethodImplementationWithReturnedTargetParameterAndNullSource() {
        Map<Long, Date> target = new HashMap<>();
        target.put( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() );
        target.put( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() );

        Map<Long, Date> returnedTarget = SourceTargetMapper.INSTANCE
            .stringStringMapToLongDateMapUsingTargetParameterAndReturn( null, target );

        assertThat( target ).isSameAs( returnedTarget );
        assertResult( target );
    }

    private void assertResult(Map<Long, Date> target) {
        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).contains(
            entry( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() ),
            entry( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() )
        );
    }

    private Map<String, String> createStringStringMap() {
        Map<String, String> values = new HashMap<>();
        values.put( "42", "01.01.1980" );
        values.put( "121", "20.07.2013" );
        return values;
    }

    @ProcessorTest
    public void shouldInvokeMapMethodImplementationForMapTypedProperty() {
        Map<Long, Date> values = new HashMap<>();
        values.put( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() );
        values.put( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() );

        Source source = new Source();
        source.setValues( values );
        source.setPublicValues( new HashMap<>( values ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).isNotNull();
        assertThat( target.getValues() ).hasSize( 2 );
        assertThat( target.getValues() ).contains(
            entry( "42", "01.01.1980" ),
            entry( "121", "20.07.2013" )
        );

        assertThat( target.publicValues )
            .isNotNull()
            .hasSize( 2 )
            .contains(
                entry( "42", "01.01.1980" ),
                entry( "121", "20.07.2013" )
            );
    }

    @ProcessorTest
    public void shouldInvokeReverseMapMethodImplementationForMapTypedProperty() {
        Map<String, String> values = createStringStringMap();

        Target target = new Target();
        target.setValues( values );
        target.publicValues = new HashMap<>( values );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getValues() ).isNotNull();
        assertThat( source.getValues() ).hasSize( 2 );
        assertThat( source.getValues() ).contains(
            entry( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() ),
            entry( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() )
        );

        assertThat( source.getPublicValues() )
            .isNotNull()
            .hasSize( 2 )
            .contains(
                entry( 42L, new GregorianCalendar( 1980, Calendar.JANUARY, 1 ).getTime() ),
                entry( 121L, new GregorianCalendar( 2013, Calendar.JULY, 20 ).getTime() )
            );
    }

    private Map<Integer, Integer> createIntIntMap() {
        Map<Integer, Integer> values = new HashMap<>();
        values.put( 42, 47 );
        values.put( 121, 123 );
        return values;
    }

    @ProcessorTest
    @IssueKey("87")
    public void shouldCreateMapMethodImplementationWithoutConversionOrElementMappingMethod() {
        Map<Integer, Integer> values = createIntIntMap();

        Map<Number, Number> target = SourceTargetMapper.INSTANCE.intIntToNumberNumberMap( values );

        assertThat( target ).isNotNull();
        assertThat( target ).hasSize( 2 );
        assertThat( target ).isEqualTo( values );
    }
}
