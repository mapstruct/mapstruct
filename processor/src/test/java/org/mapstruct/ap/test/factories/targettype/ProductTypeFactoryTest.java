/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.targettype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Remo Meier
 */
@WithClasses( { Foo9Base.class, Foo9Child.class, Bar9Base.class, Bar9Child.class, Bar9Factory.class,
    TargetTypeFactoryTestMapper.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class ProductTypeFactoryTest {

    @Test
    public void shouldUseFactoryTwoCreateBaseClassDueToTargetType() {
        Foo9Base foo9 = new Foo9Base();
        foo9.setProp( "foo9" );

        Bar9Base bar9 = TargetTypeFactoryTestMapper.INSTANCE.foo9BaseToBar9Base( foo9 );

        assertThat( bar9 ).isNotNull();
        assertThat( bar9.getProp() ).isEqualTo( "foo9" );
        assertThat( bar9.getSomeTypeProp() ).isEqualTo( "FOO9" );
    }

    @Test
    public void shouldUseFactoryTwoCreateChildClassDueToTargetType() {
        Foo9Child foo9 = new Foo9Child();
        foo9.setProp( "foo9" );
        foo9.setChildProp( "foo9Child" );

        Bar9Child bar9 = TargetTypeFactoryTestMapper.INSTANCE.foo9ChildToBar9Child( foo9 );

        assertThat( bar9 ).isNotNull();
        assertThat( bar9.getProp() ).isEqualTo( "foo9" );
        assertThat( bar9.getChildProp() ).isEqualTo( "foo9Child" );
        assertThat( bar9.getSomeTypeProp() ).isEqualTo( "FOO9" );
    }
}
