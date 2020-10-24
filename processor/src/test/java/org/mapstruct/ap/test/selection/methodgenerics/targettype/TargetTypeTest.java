/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.targettype;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class TargetTypeTest {

    @Test
    @WithClasses( PlainTargetTypeMapper.class )
    public void testPlain() {

        PlainTargetTypeMapper.Target target =
            PlainTargetTypeMapper.INSTANCE.sourceToTarget( new PlainTargetTypeMapper.Source( "15" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getProp().toPlainString() ).isEqualTo( "15" );
    }

    @Test
    @WithClasses( NestedTargetTypeMapper.class )
    public void testNestedTypeVar() {
        NestedTargetTypeMapper.Target target =
            NestedTargetTypeMapper.INSTANCE.sourceToTarget( new NestedTargetTypeMapper.Source( "test" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getProp().getWrapped() ).isEqualTo( "test" );
    }

}
