/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2505;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Sjaak derksen
 */
@IssueKey("2505")
@WithClasses( Issue2505Mapper.class )
class Issue2505Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( Issue2505Mapper.class );

    @ProcessorTest
    void shouldNotGenerateEnumMappingMethodForDeepClone() {
        Issue2505Mapper.CustomerDTO source = new Issue2505Mapper.CustomerDTO();
        source.setStatus( Issue2505Mapper.Status.DISABLED );

        Issue2505Mapper.Customer target = Issue2505Mapper.INSTANCE.map( source );

        assertThat( target.getStatus() ).isEqualTo( Issue2505Mapper.Status.DISABLED );
    }
}
