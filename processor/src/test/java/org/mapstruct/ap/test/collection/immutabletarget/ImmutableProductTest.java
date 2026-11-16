/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.immutabletarget;

import java.util.Arrays;
import java.util.Collections;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

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
        CupboardNoSetterMapper.class,
        CupboardEntityOnlyGetter.class
    })
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = CupboardNoSetterMapper.class,
                kind = javax.tools.Diagnostic.Kind.WARNING,
                line = 22,
                message = "No target property found for target \"CupboardEntityOnlyGetter\"."),
        })
    public void shouldIgnoreImmutableTarget() {
        CupboardDto in = new CupboardDto();
        in.setContent( Arrays.asList( "flour", "peas" ) );
        CupboardEntityOnlyGetter out = new CupboardEntityOnlyGetter();
        out.getContent().add( "bread" );
        CupboardNoSetterMapper.INSTANCE.map( in, out );

        assertThat( out.getContent() ).containsExactly( "bread" );
    }

}
