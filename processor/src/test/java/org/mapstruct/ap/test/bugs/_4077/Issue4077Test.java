/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4077;

import java.io.File;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/4077.
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
        String path = "src/test/resources/fixtures/org/mapstruct/ap/test/bugs/_4077/Issue4077MapperImpl.java";
        generatedSource.forMapper( Issue4077Mapper.class ).hasSameMapperContent( new File( path ) );
    }
}
