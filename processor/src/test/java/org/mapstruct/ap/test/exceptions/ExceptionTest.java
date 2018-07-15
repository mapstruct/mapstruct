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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.exceptions.imports.TestException1;
import org.mapstruct.ap.test.exceptions.imports.TestExceptionBase;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

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
@RunWith( AnnotationProcessorTestRunner.class )
public class ExceptionTest {

    @Test( expected = RuntimeException.class )
    @IssueKey( "198" )
    public void shouldThrowRuntimeInBeanMapping() throws TestException2, ParseException {
        Source source = new Source();
        source.setSize( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.sourceToTarget( source );
    }

    @Test( expected = TestException2.class )
    @IssueKey( "198" )
    public void shouldThrowTestException2InBeanMapping() throws TestException2, ParseException {
        Source source = new Source();
        source.setSize( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.sourceToTarget( source );
    }

    @Test( expected = ParseException.class )
    @IssueKey( "198" )
    public void shouldThrowTestParseExceptionInBeanMappingDueToTypeConverion() throws TestException2, ParseException {
        Source source = new Source();
        source.setDate( "nonsense" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.sourceToTarget( source );
    }

    @Test( expected = RuntimeException.class )
    @IssueKey( "198" )
    public void shouldThrowRuntimeInIterableMapping() throws TestException2 {
        List<Integer> source = new ArrayList<Integer>();
        source.add( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerListToLongList( source );
    }

    @Test( expected = TestException2.class )
    @IssueKey( "198" )
    public void shouldThrowTestException2InIterableMapping() throws TestException2 {
        List<Integer> source = new ArrayList<Integer>();
        source.add( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerListToLongList( source );
    }

    @Test( expected = RuntimeException.class )
    @IssueKey( "198" )
    public void shouldThrowRuntimeInMapKeyMapping() throws TestException2 {
        Map<Integer, String> source = new HashMap<Integer, String>();
        source.put( 1, "test" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerKeyMapToLongKeyMap( source );
    }

    @Test( expected = TestException2.class )
    @IssueKey( "198" )
    public void shouldThrowTestException2InMapKeyMapping() throws TestException2 {
        Map<Integer, String> source = new HashMap<Integer, String>();
        source.put( 2, "test" );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerKeyMapToLongKeyMap( source );
    }

    @Test( expected = RuntimeException.class )
    @IssueKey( "198" )
    public void shouldThrowRuntimeInMapValueMapping() throws TestException2 {
        Map<String, Integer> source = new HashMap<String, Integer>();
        source.put( "test", 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerValueMapToLongValueMap( source );
    }

    @Test( expected = TestException2.class )
    @IssueKey( "198" )
    public void shouldThrowTestException2InMapValueMapping() throws TestException2 {
        Map<String, Integer> source = new HashMap<String, Integer>();
        source.put( "test", 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.integerValueMapToLongValueMap( source );
    }

    @Test( expected = RuntimeException.class )
    @IssueKey( "198" )
    public void shouldThrowRuntimeInBeanMappingViaBaseException() throws TestExceptionBase {
        Source source = new Source();
        source.setSize( 1 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.sourceToTargetViaBaseException( source );
    }

    @Test( expected = TestException2.class )
    @IssueKey( "198" )
    public void shouldThrowTestException2InBeanMappingViaBaseException() throws TestExceptionBase {
        Source source = new Source();
        source.setSize( 2 );
        SourceTargetMapper sourceTargetMapper = SourceTargetMapper.INSTANCE;
        sourceTargetMapper.sourceToTargetViaBaseException( source );
    }
}
