/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.immutabletarget;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 *
 * @author Sjaak Derksen
 */
@WithClasses({CupboardDto.class, CupboardEntity.class, CupboardMapper.class})
@IssueKey( "1126" )
public class ImmutableProductTest {

    @ProcessorTest
    public void shouldHandleImmutableTarget() {

        CupboardDto in = new CupboardDto();
        in.setContent( Arrays.asList( "cups", "soucers" ) );
        CupboardEntity out = new CupboardEntity();
        out.setContent( Collections.emptyList() );

        CupboardMapper.INSTANCE.map( in, out );

        assertThat( out.getContent() ).isNotNull();
        assertThat( out.getContent() ).containsExactly( "cups", "soucers"  );
    }

    @ProcessorTest
    @WithClasses({
        ErroneousCupboardMapper.class,
        CupboardEntityOnlyGetter.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCupboardMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 22,
                message = "No write accessor found for property \"content\" in target type.")
        }
    )
    public void testShouldFailOnPropertyMappingNoPropertySetterOnlyGetter() {
    }

}
