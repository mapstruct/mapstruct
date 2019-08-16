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
    CharSource.class,
    CharTarget.class,
    CharMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class CharConversionTest {

    @Test
    public void shouldApplyCharConversion() {
        CharSource source = new CharSource();
        source.setC( 'G' );

        CharTarget target = CharMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getC() ).isEqualTo( Character.valueOf( 'G' ) );
    }

    @Test
    public void shouldApplyReverseCharConversion() {
        CharTarget target = new CharTarget();
        target.setC( 'G' );

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getC() ).isEqualTo( 'G' );
    }

    @Test
    @IssueKey( "229" )
    public void wrapperToPrimitveIsNullSafe() {
        CharTarget target = new CharTarget();

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
    }
}
