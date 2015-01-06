/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.mapperconfig;

import static org.fest.assertions.Assertions.assertThat;

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
@IssueKey( "102" )
@WithClasses( {
    Source.class,
    Target.class,
    FooDto.class,
    FooEntity.class,
    CentralConfig.class,
    CustomMapperViaMapper.class,
    CustomMapperViaMapperConfig.class,
    SourceTargetMapper.class
} )
@RunWith(AnnotationProcessorTestRunner.class)
public class ConfigTest {

    @Test
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperForFooToEntity() {

        Target target = SourceTargetMapper.INSTANCE.toTarget( new Source( new FooDto() ) );
        assertThat( target.getFoo() ).isNotNull();
        assertThat( target.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapper.class.getSimpleName() );
    }

    @Test
    @WithClasses( { Target.class, SourceTargetMapper.class } )
    public void shouldUseCustomMapperViaMapperConfigForFooToDto() {

        Source source = SourceTargetMapper.INSTANCE.toSource( new Target( new FooEntity() ) );
        assertThat( source.getFoo() ).isNotNull();
        assertThat( source.getFoo().getCreatedBy() ).isEqualTo( CustomMapperViaMapperConfig.class.getSimpleName() );
    }

    @Test
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperWarn.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperWarn.class,
                kind = javax.tools.Diagnostic.Kind.WARNING, line = 37,
                messageRegExp = "Unmapped target property: \"noFoo\"")
        })
    public void shouldUseWARNViaMapper() {
    }

    @Test
    @WithClasses( { TargetNoFoo.class, SourceTargetMapperError.class } )
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapperError.class,
                kind = javax.tools.Diagnostic.Kind.ERROR, line = 33,
                messageRegExp = "Unmapped target property: \"noFoo\"")
        })
    public void shouldUseERRORViaMapperConfig() {
    }
}
