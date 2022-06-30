/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.nativetypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    CharSource.class,
    CharTarget.class,
    CharMapper.class
})
public class CharConversionTest {

    @ProcessorTest
    public void shouldApplyCharConversion() {
        CharSource source = new CharSource();
        source.setC( 'G' );

        CharTarget target = CharMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getC() ).isEqualTo( Character.valueOf( 'G' ) );
    }

    @ProcessorTest
    public void shouldApplyReverseCharConversion() {
        CharTarget target = new CharTarget();
        target.setC( 'G' );

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getC() ).isEqualTo( 'G' );
    }

    @ProcessorTest
    @IssueKey( "229" )
    public void wrapperToPrimitiveIsNullSafe() {
        CharTarget target = new CharTarget();

        CharSource source = CharMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
    }
}
