/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.typegenerics;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 *
 */
public class WildCardTest {

    @ProcessorTest
    @WithClasses( SourceWildCardExtendsMapper.class )
    public void testWildCard() {

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

}
