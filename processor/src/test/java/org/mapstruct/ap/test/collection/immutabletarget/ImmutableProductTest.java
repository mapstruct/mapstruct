/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.collection.immutabletarget;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Sjaak Derksen
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({CupboardDto.class, CupboardEntity.class, CupboardMapper.class})
@IssueKey( "1126" )
public class ImmutableProductTest {

    @Test
    public void shouldHandleImmutableTarget() {

        CupboardDto in = new CupboardDto();
        in.setContent( Arrays.asList( "cups", "soucers" ) );
        CupboardEntity out = new CupboardEntity();
        out.setContent( Collections.<String>emptyList() );

        CupboardMapper.INSTANCE.map( in, out );

        assertThat( out.getContent() ).isNotNull();
        assertThat( out.getContent() ).containsExactly( "cups", "soucers"  );
    }

    @Test
    @WithClasses({
        ErroneousCupboardMapper.class,
        CupboardEntityOnlyGetter.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCupboardMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 35,
                messageRegExp = "No write accessor found for property \"content\" in target type.")
        }
    )
    public void testShouldFailOnPropertyMappingNoPropertySetterOnlyGetter() {
    }

}
