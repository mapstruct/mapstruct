/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("3539")
public class BeanMappingIgnoreTargetTest {

    @ProcessorTest
    @WithClasses( {Source.class, Target.class, BeanMappingIgnoreTargetMapper.class} )
    @BeanMapping
    public void shouldIgnoreTargetProperty() {
        BeanMappingIgnoreTargetMapper.INSTANCE.convert( new Source() );
    }
}
