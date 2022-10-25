/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3057;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.test.bugs._3057.SelfMapper.Source;
import org.mapstruct.ap.test.bugs._3057.SelfMapper.Target;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@WithClasses( SelfMapper.class )
public class SelfMapperTest {

    @ProcessorTest
    void mapsSelf() {
        Source sourceOuter = new SelfMapper.Source();
        Source sourceInner = new SelfMapper.Source();
        sourceOuter.self = sourceInner;

        Target targetOuter = SelfMapper.INSTANCE.map( sourceOuter );

        assertThat( targetOuter.value ).isEqualTo( "constantValue" );
        assertThat( targetOuter.self.value ).isEqualTo( "constantValue" );
    }
}
