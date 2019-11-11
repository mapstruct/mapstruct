/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@WithClasses({
    BooleanSource.class,
    BooleanTarget.class,
    BooleanMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BooleanConversionTest {

    @Test
    public void shouldApplyBooleanConversion() {
        BooleanSource source = new BooleanSource();
        source.setB( true );
        source.setBool( true );

        BooleanTarget target = BooleanMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getB() ).isEqualTo( Boolean.TRUE );
        assertThat( target.getBool() ).isEqualTo( Boolean.TRUE );
    }

    @Test
    public void shouldApplyReverseBooleanConversion() {
        BooleanTarget target = new BooleanTarget();
        target.setB( Boolean.TRUE );
        target.setBool( Boolean.TRUE );

        BooleanSource source = BooleanMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.isB() ).isEqualTo( true );
        assertThat( source.getBool() ).isEqualTo( true );
    }

    @Test
    @IssueKey( "229" )
    public void wrapperToPrimitiveIsNullSafe() {
        BooleanTarget target = new BooleanTarget();

        BooleanSource source = BooleanMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
    }
}
