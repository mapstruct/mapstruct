/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.nestedgenerics;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 *
 */
@RunWith( AnnotationProcessorTestRunner.class)
public class NestedGenericsTest {

    @Test
    @WithClasses( ReturnTypeHasNestedTypeVarMapper.class )
    public void testGenericReturnTypeVar() {

        ReturnTypeHasNestedTypeVarMapper.Source source = new ReturnTypeHasNestedTypeVarMapper.Source("test" );
        ReturnTypeHasNestedTypeVarMapper.Target target = ReturnTypeHasNestedTypeVarMapper.INSTANCE.toTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).hasSize( 1 );
        assertThat( target.getProp().get( 0 ) ).contains( "test" );
    }

    @Test
    @WithClasses( SourceTypeHasNestedTypeVarMapper.class )
    public void testGenericSourceTypeVar() {

        SourceTypeHasNestedTypeVarMapper.Source src =
            new SourceTypeHasNestedTypeVarMapper.Source( Collections.singletonList( Collections.singleton( "test" ) ) );
        SourceTypeHasNestedTypeVarMapper.Target target = SourceTypeHasNestedTypeVarMapper.INSTANCE.toTarget( src );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "test" );

    }

}
