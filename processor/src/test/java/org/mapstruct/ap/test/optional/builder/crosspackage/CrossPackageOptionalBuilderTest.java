/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder.crosspackage;

import java.util.Optional;

import org.mapstruct.ap.test.optional.builder.crosspackage.dto.PersonTarget;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey( "4046" )
@WithClasses({ CrossPackageOptionalBuilderMapper.class, PersonSource.class, PersonTarget.class })
class CrossPackageOptionalBuilderTest {

    @ProcessorTest
    void shouldUseStaticBuilderMethodForOptionalTargetAcrossPackages() {
        PersonSource source = new PersonSource( "John" );
        Optional<PersonTarget> result = CrossPackageOptionalBuilderMapper.INSTANCE.toTarget( source );

        assertThat( result ).isPresent();
        assertThat( result.get().getName() ).isEqualTo( "John" );
    }
}
