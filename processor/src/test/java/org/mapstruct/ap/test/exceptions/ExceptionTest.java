/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.exceptions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.test.exceptions.imports.TestException1;
import org.mapstruct.ap.test.exceptions.imports.TestExceptionBase;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses( {
    Source.class,
    Target.class,
    SourceTargetMapper.class,
    ExceptionTestMapper.class,
    ExceptionTestDecorator.class,
    TestExceptionBase.class,
    TestException1.class,
    TestException2.class } )
public class ExceptionTest {

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowRuntimeInBeanMapping() throws TestException2, ParseException {
        Source source = new Source();
        source.setSize( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.sourceToTarget( source ) )
            .isInstanceOf( RuntimeException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestException2InBeanMapping() throws TestException2, ParseException {
        Source source = new Source();
        source.setSize( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.sourceToTarget( source ) )
            .isInstanceOf( TestException2.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestParseExceptionInBeanMappingDueToTypeConverion() throws TestException2, ParseException {
        Source source = new Source();
        source.setDate( "nonsense" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.sourceToTarget( source ) )
            .isInstanceOf( ParseException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowRuntimeInIterableMapping() throws TestException2 {
        List<Integer> source = new ArrayList<>();
        source.add( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerListToLongList( source ) )
            .isInstanceOf( RuntimeException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestException2InIterableMapping() throws TestException2 {
        List<Integer> source = new ArrayList<>();
        source.add( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerListToLongList( source ) )
            .isInstanceOf( TestException2.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowRuntimeInMapKeyMapping() throws TestException2 {
        Map<Integer, String> source = new HashMap<>();
        source.put( 1, "test" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerKeyMapToLongKeyMap( source ) )
            .isInstanceOf( RuntimeException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestException2InMapKeyMapping() throws TestException2 {
        Map<Integer, String> source = new HashMap<>();
        source.put( 2, "test" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerKeyMapToLongKeyMap( source ) )
            .isInstanceOf( TestException2.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowRuntimeInMapValueMapping() throws TestException2 {
        Map<String, Integer> source = new HashMap<>();
        source.put( "test", 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerValueMapToLongValueMap( source ) )
            .isInstanceOf( RuntimeException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestException2InMapValueMapping() throws TestException2 {
        Map<String, Integer> source = new HashMap<>();
        source.put( "test", 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.integerValueMapToLongValueMap( source ) )
            .isInstanceOf( TestException2.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowRuntimeInBeanMappingViaBaseException() throws TestExceptionBase {
        Source source = new Source();
        source.setSize( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.sourceToTargetViaBaseException( source ) )
            .isInstanceOf( RuntimeException.class );
    }

    @ProcessorTest
    @IssueKey( "198" )
    public void shouldThrowTestException2InBeanMappingViaBaseException() throws TestExceptionBase {
        Source source = new Source();
        source.setSize( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        assertThatThrownBy( () -> sourceTargetMapper.sourceToTargetViaBaseException( source ) )
            .isInstanceOf( TestException2.class );
    }
}
