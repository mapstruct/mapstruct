/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.simple.Source;
import org.mapstruct.itest.simple.SourceTargetMapper;
import org.mapstruct.itest.simple.Target;

public class ConversionTest {

    @Test
    public void shouldApplyConversions() {
        Source source = new Source();
        source.setFoo( 42 );
        source.setBar( 23L );
        source.setZip( 73 );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
        assertThat( target.getZip() ).isEqualTo( "73" );
    }

    @Test
    public void shouldHandleNulls() {
        Source source = new Source();
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 1 ) );
        assertThat( target.getBar() ).isEqualTo( 0 );
        assertThat( target.getZip() ).isEqualTo( "0" );
    }

    @Test
    public void shouldApplyConversionsToMappedProperties() {
        Source source = new Source();
        source.setQax( 42 );
        source.setBaz( 23L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getBaz() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getQax() ).isEqualTo( 23 );
    }

    @Test
    public void shouldApplyConversionsForReverseMapping() {
        Target target = new Target();
        target.setFoo( 42L );
        target.setBar( 23 );
        target.setZip( "73" );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getFoo() ).isEqualTo( 42 );
        assertThat( source.getBar() ).isEqualTo( 24 );
        assertThat( source.getZip() ).isEqualTo( 73 );
    }

    @Test
    public void shouldApplyConversionsToMappedPropertiesForReverseMapping() {
        Target target = new Target();
        target.setQax( 42 );
        target.setBaz( 23L );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getBaz() ).isEqualTo( 43 );
        assertThat( source.getQax() ).isEqualTo( 23 );
    }

    @Test
    public void shouldWorkWithAbstractClass() {
        Source source = new Source();
        source.setFoo( 42 );
        source.setBar( 23L );
        source.setZip( 73 );

        Target target = SourceTargetAbstractMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( Long.valueOf( 43 ) );
        assertThat( target.getBar() ).isEqualTo( 23 );
        assertThat( target.getZip() ).isEqualTo( "73" );
    }
}
