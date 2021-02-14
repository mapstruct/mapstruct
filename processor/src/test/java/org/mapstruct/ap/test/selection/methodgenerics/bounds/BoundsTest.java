/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.bounds;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Sjaak Derksen
 *
 */
public class BoundsTest {

    @ProcessorTest
    @WithClasses( SourceTypeIsBoundedTypeVarMapper.class )
    public void testGenericSourceTypeVar() {

        SourceTypeIsBoundedTypeVarMapper.Source source = new SourceTypeIsBoundedTypeVarMapper.Source( "5", "test" );
        SourceTypeIsBoundedTypeVarMapper.Target target =
            SourceTypeIsBoundedTypeVarMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isEqualTo( 5L );
        assertThat( target.getProp2().getProp() ).isEqualTo( "test" );

    }

}
