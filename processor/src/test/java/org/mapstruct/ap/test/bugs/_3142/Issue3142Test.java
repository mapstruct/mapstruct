/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3142;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue3142Exception.class,
    Source.class,
    Target.class,
})
@IssueKey("3142")
class Issue3142Test {

    @ProcessorTest
    @WithClasses({
        Issue3142WithBeforeMappingExceptionMapper.class
    })
    void exceptionThrownInBeforeMapping() {
        assertThatThrownBy( () -> Issue3142WithBeforeMappingExceptionMapper.INSTANCE.map(
            new Source( new Source.Nested( "throwException" ) ), "test" )
        )
            .isInstanceOf( Issue3142Exception.class )
            .hasMessage( "Source nested exception" );
    }

    @ProcessorTest
    @WithClasses({
        Issue3142WithAfterMappingExceptionMapper.class
    })
    void exceptionThrownInAfterMapping() {
        assertThatThrownBy( () -> Issue3142WithAfterMappingExceptionMapper.INSTANCE.map(
            new Source( new Source.Nested( "throwException" ) ), "test" )
        )
            .isInstanceOf( Issue3142Exception.class )
            .hasMessage( "Source nested exception" );
    }
}
