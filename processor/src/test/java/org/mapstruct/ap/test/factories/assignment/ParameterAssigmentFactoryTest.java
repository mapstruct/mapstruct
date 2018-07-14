/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Remo Meier
 */
@WithClasses( { Bar5.class, Foo5A.class, Foo5B.class, Bar6.class, Foo6A.class, Foo6B.class, Bar7.class, Foo7A.class,
    Foo7B.class, Bar5Factory.class, Bar6Factory.class, Bar7Factory.class, ParameterAssignmentFactoryTestMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class ParameterAssigmentFactoryTest {

    @Test
    public void shouldUseFactoryMethodWithMultipleParams() {
        Foo5A foo5a = new Foo5A();
        foo5a.setPropB( "foo5a" );

        Foo5B foo5b = new Foo5B();
        foo5b.setPropB( "foo5b" );

        Bar5 bar5 = ParameterAssignmentFactoryTestMapper.INSTANCE.foos5ToBar5( foo5a, foo5b );

        // foo5a and foo5b get merged into bar5 by a custom factory
        assertThat( bar5 ).isNotNull();
        assertThat( bar5.getPropA() ).isEqualTo( "foo5a" );
        assertThat( bar5.getPropB() ).isEqualTo( "foo5b" );
        assertThat( bar5.getSomeTypeProp0() ).isEqualTo( "FOO5A" );
        assertThat( bar5.getSomeTypeProp1() ).isEqualTo( "foo5b" );
    }

    @Test
    public void shouldUseFactoryMethodWithFirstParamsOfMappingMethod() {
        Foo6A foo6a = new Foo6A();
        foo6a.setPropB( "foo6a" );

        Foo6B foo6b = new Foo6B();
        foo6b.setPropB( "foo6b" );

        Bar6 bar6 = ParameterAssignmentFactoryTestMapper.INSTANCE.foos6ToBar6( foo6a, foo6b );

        assertThat( bar6 ).isNotNull();
        assertThat( bar6.getPropA() ).isEqualTo( "foo6a" );
        assertThat( bar6.getPropB() ).isEqualTo( "foo6b" );
        assertThat( bar6.getSomeTypeProp0() ).isEqualTo( "FOO6A" );
    }

    @Test
    public void shouldUseFactoryMethodWithSecondParamsOfMappingMethod() {
        Foo7A foo7a = new Foo7A();
        foo7a.setPropB( "foo7a" );

        Foo7B foo7b = new Foo7B();
        foo7b.setPropB( "foo7b" );

        Bar7 bar7 = ParameterAssignmentFactoryTestMapper.INSTANCE.foos7ToBar7( foo7a, foo7b );

        assertThat( bar7 ).isNotNull();
        assertThat( bar7.getPropA() ).isEqualTo( "foo7a" );
        assertThat( bar7.getPropB() ).isEqualTo( "foo7b" );
        assertThat( bar7.getSomeTypeProp0() ).isEqualTo( "FOO7B" );
    }
}
