/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Reproducer for <a href="https://github.com/mapstruct/mapstruct/issues/4077">4077</a>.
 *
 * @author Agustin Ranieri
 */
@IssueKey( "4077" )
@WithJSpecify
public class Issue4077Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( { Issue4077Mapper.class, Source.class, Target.class } )
    public void shouldAddNullCheckBeforeReusingMethod() {
        generatedSource.addComparisonToFixtureFor( Issue4077Mapper.class );
    }
}
