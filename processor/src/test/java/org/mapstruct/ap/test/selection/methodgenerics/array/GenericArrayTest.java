/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.array;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 *
 */
public class GenericArrayTest {

    @ProcessorTest
    @WithClasses( ReturnTypeIsTypeVarArrayMapper.class )
    public void testGenericReturnTypeVar() {

        ReturnTypeIsTypeVarArrayMapper.GenericWrapper<String> wrapper =
            new ReturnTypeIsTypeVarArrayMapper.GenericWrapper<>( "test" );
        ReturnTypeIsTypeVarArrayMapper.Source source = new ReturnTypeIsTypeVarArrayMapper.Source( wrapper );

        ReturnTypeIsTypeVarArrayMapper.Target target = ReturnTypeIsTypeVarArrayMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).containsExactly( "test" );
    }

    @ProcessorTest
    @WithClasses( SourceTypeIsTypeVarArrayMapper.class )
    public void testGenericSourceTypeVar() {

        SourceTypeIsTypeVarArrayMapper.Source source =
            new SourceTypeIsTypeVarArrayMapper.Source( new String[] { "test" } );
        SourceTypeIsTypeVarArrayMapper.Target target = SourceTypeIsTypeVarArrayMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getWrapped() ).isEqualTo( "test" );

    }

    @ProcessorTest
    @WithClasses( BothParameterizedMapper.class )
    public void testBothParameterized() {

        BothParameterizedMapper.GenericSourceWrapper<String[]> wrapper =
            new BothParameterizedMapper.GenericSourceWrapper<>( new String[] { "test" } );
        BothParameterizedMapper.Source source = new BothParameterizedMapper.Source( wrapper );
        BothParameterizedMapper.Target target = BothParameterizedMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getWrapped() ).containsExactly( "test" );

    }
}
