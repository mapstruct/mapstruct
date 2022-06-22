/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._849;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses({ Source.class, Target.class, Issue849Mapper.class })
public class Issue849Test {

    @ProcessorTest
    @IssueKey("849")
    public void shouldCompileWithAllImportsDeclared() {

        Source source = new Source();
        source.setSourceList( Arrays.asList( "test" ) );

        Target target = Issue849Mapper.INSTANCE.mapSourceToTarget( source );
        assertThat( target.getTargetList() ).containsExactly( "test" );

    }
}
