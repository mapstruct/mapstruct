/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.qualified;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Remo Meier
 */
@WithClasses( { Foo10.class, Bar10.class, TestQualifier.class, Bar10Factory.class, QualifiedFactoryTestMapper.class } )
public class QualifiedFactoryTest {

    @ProcessorTest
    public void shouldUseFactoryWithoutQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @ProcessorTest
    public void shouldUseFactoryWithQualifier() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Lower( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "foo10" );
    }

    @ProcessorTest
    public void shouldUseFactoryWithQualifierName() {
        Foo10 foo10 = new Foo10();
        foo10.setProp( "foo10" );

        Bar10 bar10 = QualifiedFactoryTestMapper.INSTANCE.foo10ToBar10Camel( foo10 );

        assertThat( bar10 ).isNotNull();
        assertThat( bar10.getProp() ).isEqualTo( "foo10" );
        assertThat( bar10.getSomeTypeProp() ).isEqualTo( "Foo10" );
    }
}
