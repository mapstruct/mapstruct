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
package org.mapstruct.ap.test.java8stream.forged;

import static org.assertj.core.api.Assertions.assertThat;

import javax.tools.Diagnostic.Kind;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for mappings between collection and stream types,
 *
 * @author Filip Hrisafov
 */
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class ForgedStreamMappingTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({ StreamMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethod() {

        Source source = new Source();
        source.setFooStream( Collections.asSet( "1", "2" ).stream() );

        Target target = StreamMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).contains( 1L, 2L );

        Source source2 = StreamMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).contains( "1", "2" );

        generatedSource.forMapper( StreamMapper.class )
            .content()
            .as( "Mapper should not uas addAll" )
            .doesNotContain( "addAll( " )
            .as( "Mapper should not use Stream.empty()" )
            .doesNotContain( "Stream.empty()" );
    }

    @Test
    @WithClasses({
        ErroneousStreamNonMappableStreamMapper.class,
        ErroneousNonMappableStreamSource.class,
        ErroneousNonMappableStreamTarget.class,
        Foo.class,
        Bar.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamNonMappableStreamMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Can't map Stream element \".* nonMappableStream\" to \".* nonMappableStream\". "
                    + "Consider to declare/implement a mapping method: .*."),
        }
    )
    public void shouldGenerateNonMappableMethodForSetMapping() {
    }

    @Test
    @WithClasses({ StreamMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnNullOnNullSource() {

        Source source = new Source();
        source.setFooStream( null );
        source.setFooStream3( null );

        Target target = StreamMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).isNull();

        Source source2 = StreamMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).isNull();
        assertThat( source2.getFooStream3() ).isNull();
    }

    @Test
    @WithClasses({ StreamMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setFooStream( null );
        source.setFooStream3( null );

        Target target = StreamMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooStream() ).isEmpty();
        assertThat( target.getFooStream3() ).isEmpty();

        //The empty stream was already consumed so need to set a new one
        target.setFooStream3( null );

        Source source2 = StreamMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooStream() ).isEmpty();
        assertThat( source2.getFooStream3() ).isEmpty();
    }
}
