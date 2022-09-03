/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2897;

import org.mapstruct.ap.test.bugs._2897.util.Util;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2897")
@WithClasses({
    Util.class,
    Issue2897Mapper.class,
    Source.class,
    Target.class,
})
class Issue2897Test {

    @ProcessorTest
    void shouldImportNestedClassInMapperImports() {
        Target target = Issue2897Mapper.INSTANCE.map( new Source( "test" ) );

        assertThat( target.getValue() ).isEqualTo( "parsed(test)" );
    }
}
