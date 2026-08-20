/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4086")
@WithJSpecify
@WithClasses(Issue4086Mapper.class)
class Issue4086Test {

    @ProcessorTest(Compiler.JDK)
    void shouldUseUnannotatedMappingMethodResultForNonNullConstructorParameter() {
        Issue4086Mapper.Target target = Issue4086Mapper.INSTANCE.map( new Issue4086Mapper.Source() );

        assertThat( target.getPayload().getValue() ).isEqualTo( "default" );
    }
}
