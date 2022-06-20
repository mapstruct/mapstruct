/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2505;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

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
