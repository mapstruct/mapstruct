/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.primitives;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 *
 * @author Sjaak Derksen
 */
@IssueKey("205")
@WithClasses({ Source.class, Target.class, MyLong.class })
public class PrimitiveVsWrappedSelectionTest {

    @ProcessorTest
    @WithClasses( { WrappedMapper.class, PrimitiveMapper.class, SourceTargetMapper.class } )
    public void shouldAlwaysSelectWrappedAndExplicitlyTypeConvertWrappedtoPrimitive() {

        WrappedMapper.setCalledUpon( false );
        PrimitiveMapper.setCalledUpon( false );

        Source source = new Source();
        source.setPrimitiveInt( 15 );
        source.setWrappedInt( 12 );

        Target target = SourceTargetMapper.INSTANCE.toTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getPrimitiveInt().getValue() ).isEqualTo( 15L );
        assertThat( target.getWrappedInt().getValue() ).isEqualTo( 12L );

        assertThat( WrappedMapper.isCalledUpon() ).isTrue();
        assertThat( PrimitiveMapper.isCalledUpon() ).isFalse();

    }

    @ProcessorTest
    @WithClasses( {  PrimitiveMapper.class, SourceTargetMapperPrimitive.class } )
    public void shouldSelectPrimitiveMapperAlsoForWrapped() {

        WrappedMapper.setCalledUpon( false );
        PrimitiveMapper.setCalledUpon( false );

        Source source = new Source();
        source.setPrimitiveInt( 15 );
        source.setWrappedInt( 12 );

        Target target = SourceTargetMapperPrimitive.INSTANCE.toTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getPrimitiveInt().getValue() ).isEqualTo( 15L );
        assertThat( target.getWrappedInt().getValue() ).isEqualTo( 12L );

        assertThat( WrappedMapper.isCalledUpon() ).isFalse();
        assertThat( PrimitiveMapper.isCalledUpon() ).isTrue();

    }

    @ProcessorTest
    @WithClasses( { WrappedMapper.class, SourceTargetMapperWrapped.class } )
    public void shouldSelectWrappedMapperAlsoForPrimitive() {

        WrappedMapper.setCalledUpon( false );
        PrimitiveMapper.setCalledUpon( false );

        Source source = new Source();
        source.setPrimitiveInt( 15 );
        source.setWrappedInt( 12 );

        Target target = SourceTargetMapperWrapped.INSTANCE.toTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getPrimitiveInt().getValue() ).isEqualTo( 15L );
        assertThat( target.getWrappedInt().getValue() ).isEqualTo( 12L );

        assertThat( WrappedMapper.isCalledUpon() ).isTrue();
        assertThat( PrimitiveMapper.isCalledUpon() ).isFalse();

    }
}
