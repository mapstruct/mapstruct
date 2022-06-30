/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.plain;

import java.util.Collections;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Sjaak Derksen
 *
 */
public class PlainTest {

    @ProcessorTest
    @WithClasses( ReturnTypeIsTypeVarMapper.class )
    public void testGenericReturnTypeVar() {

        ReturnTypeIsTypeVarMapper.Source source =
            new ReturnTypeIsTypeVarMapper.Source( new ReturnTypeIsTypeVarMapper.GenericWrapper<>( "test" ) );
        ReturnTypeIsTypeVarMapper.Target target = ReturnTypeIsTypeVarMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "test" );
    }

    @ProcessorTest
    @WithClasses( SourceTypeIsTypeVarMapper.class )
    public void testGenericSourceTypeVar() {

        SourceTypeIsTypeVarMapper.Source source = new SourceTypeIsTypeVarMapper.Source( "test" );
        SourceTypeIsTypeVarMapper.Target target = SourceTypeIsTypeVarMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getWrapped() ).isEqualTo( "test" );

    }

    @ProcessorTest
    @WithClasses( BothParameterizedMapper.class )
    public void testBothParameterized() {

        BothParameterizedMapper.Source source =
            new BothParameterizedMapper.Source( new BothParameterizedMapper.GenericSourceWrapper<>( "test" ) );
        BothParameterizedMapper.Target target = BothParameterizedMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getWrapped() ).isEqualTo( "test" );

    }

    @ProcessorTest
    @WithClasses( ReturnTypeIsRawTypeMapper.class )
    public void testRaw() {

        ReturnTypeIsRawTypeMapper.Source source = new ReturnTypeIsRawTypeMapper.Source( Collections.singleton( 5 ) );

        ReturnTypeIsRawTypeMapper.Target target = ReturnTypeIsRawTypeMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().iterator().next() ).isEqualTo( "5" );
    }
}
