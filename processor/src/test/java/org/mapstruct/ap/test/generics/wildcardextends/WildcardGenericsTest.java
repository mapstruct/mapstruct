/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcardextends;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2947" )
@WithClasses( { Source.class, Target.class, WildcardedInterface.class } )
class WildcardGenericsTest {

    @ProcessorTest
    @WithClasses( { WildcardExtendsMapper.class } )
    void mapsWithWildcardSuccesfully() {
        Source<WildcardedInterfaceImpl> source = new Source<>();
        source.setObject( new WildcardedInterfaceImpl() );
        source.getObject().setContents( "Test contents" );

        Target target = WildcardExtendsMapper.INSTANCE.map( source );

        assertThat( target.getObject() ).isEqualTo( "Test contents" );
    }
}
