/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.qualified;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Remo Meier
 */
@WithClasses( { Foo10.class, Bar10.class, TestQualifier.class, Bar10Factory.class, QualifiedFactoryTestMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class QualifiedFactoryTest {

    @Test
    public void shouldUseFactoryWithoutQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @Test
    public void shouldUseFactoryWithQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @Test
    public void shouldUseFactoryWithQualifierName() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Camel( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "Foo10" );
    }
}
