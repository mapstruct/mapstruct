/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.reuse;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

public class ReuseSubclassMappingMethodTest {

    @ProcessorTest
    @WithClasses({
            ReuseMapper.class, AbstractSource.class, Source1.class, SourceHolder.class,
    AbstractTarget.class, Target1.class, TargetHolder.class})
    public void shouldAddNullCheckBeforeReusingSubclassMappingMethod() {
        SourceHolder sourceHolder = new SourceHolder();
        sourceHolder.setH( null );
        TargetHolder targetHolder = ReuseMapper.INSTANCE.map( sourceHolder );
        assertThat( targetHolder ).isNotNull();
        assertThat( targetHolder.getH() ).isNull();
    }
}
