/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.wildcards;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class WildCardTest {

    @Test
    @WithClasses( SourceWildCardExtendsMapper.class )
    public void testWildCardAsSourceType() {

        // prepare source
        SourceWildCardExtendsMapper.Wrapper<BigInteger> wrapper =
            new SourceWildCardExtendsMapper.Wrapper<>( new BigInteger( "5" ) );
        SourceWildCardExtendsMapper.Source source = new SourceWildCardExtendsMapper.Source( wrapper );

        // action
        SourceWildCardExtendsMapper.Target target = SourceWildCardExtendsMapper.INSTANCE.map( source );

        // verify target
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isEqualTo( "5" );
    }

    @Test
    @WithClasses( ReturnTypeWildCardExtendsMapper.class )
    public void testWildCardAsReturnType() {

        // prepare source
        ReturnTypeWildCardExtendsMapper.Source source = new ReturnTypeWildCardExtendsMapper.Source( "5" );

        // action
        ReturnTypeWildCardExtendsMapper.Target target = ReturnTypeWildCardExtendsMapper.INSTANCE.map( source );

        // verify target
        assertThat( target ).isNotNull();
        assertThat( target.getProp() ).isNotNull();
        assertThat( target.getProp().getWrapped() ).isEqualTo( BigInteger.valueOf( 5 ) );

    }

}
