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
package org.mapstruct.ap.test.collection.forged;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import com.google.common.collect.ImmutableMap;

/**
 * Test for mappings between collection types,
 *
 * @author Sjaak Derksen
 */
@IssueKey( "4" )
@RunWith(AnnotationProcessorTestRunner.class)
public class CollectionMappingTest {

    @Test
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethod() {

        Source source = new Source();
        source.setFooSet( Collections.asSet( "1", "2" ) );
        source.publicFooSet = Collections.asSet( "3", "4" );

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isEqualTo( Collections.asSet( 1L, 2L ) );
        assertThat( target.getPublicFooSet() ).isEqualTo( Collections.asSet( 3L, 4L ) );

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isEqualTo( Collections.asSet( "1", "2" ) );
        assertThat( source2.publicFooSet ).isEqualTo( Collections.asSet( "3", "4" ) );
    }

    @Test
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethod() {

        Map<String, Long> sourceMap = ImmutableMap.<String, Long>builder().put( "rabbit", 1L ).build();
        Source source = new Source();
        source.setBarMap( sourceMap );
        source.publicBarMap = ImmutableMap.<String, Long>builder().put( "fox", 2L ).build();

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        Map<String, String> targetMap = ImmutableMap.<String, String>builder().put( "rabbit", "1" ).build();
        Map<String, String> targetMap2 = ImmutableMap.<String, String>builder().put( "fox", "2" ).build();
        assertThat( target.getBarMap() ).isEqualTo( targetMap );
        assertThat( target.getPublicBarMap() ).isEqualTo( targetMap2 );

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isEqualTo( sourceMap );
        assertThat( source2.publicBarMap ).isEqualTo( source.publicBarMap );
    }

    @Test
    @WithClasses({ ErroneousCollectionNonMappableSetMapper.class,
        ErroneousNonMappableSetSource.class,
        ErroneousNonMappableSetTarget.class,
        Foo.class,
        Bar.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNonMappableSetMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Can't map Collection element \".* nonMappableSet\" to \".* nonMappableSet\". "
                    + "Consider to declare/implement a mapping method: .*."),
        }
    )
    public void shouldGenerateNonMappleMethodForSetMapping() {
    }

   @Test
    @WithClasses({ ErroneousCollectionNonMappableMapMapper.class,
        ErroneousNonMappableMapSource.class,
        ErroneousNonMappableMapTarget.class,
        Foo.class,
        Bar.class
    })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNonMappableMapMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Can't map Map key \".* nonMappableMap\\{:key\\}\" to \".* nonMappableMap\\{:key\\}\". "
                    + "Consider to declare/implement a mapping method: .*."),
            @Diagnostic(type = ErroneousCollectionNonMappableMapMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Can't map Map value \".* nonMappableMap\\{:value\\}\" to \".* " +
                    "nonMappableMap\\{:value\\}\". Consider to declare/implement a mapping method: .*."),
        }
    )
    public void shouldGenerateNonMappleMethodForMapMapping() {
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnNullOnNullSource() {

        Source source = new Source();
        source.setFooSet( null );
        source.publicFooSet = null;

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isNull();
        assertThat( target.getPublicFooSet() ).isNull();

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isNull();
        assertThat( source2.publicFooSet ).isNull();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethodReturnNullOnNullSource() {

        Source source = new Source();
        source.setBarMap( null );
        source.publicBarMap = null;

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getBarMap() ).isNull();
        assertThat( target.getPublicBarMap() ).isNull();

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isNull();
        assertThat( source2.publicBarMap ).isNull();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setFooSet( null );
        source.publicFooSet = null;

        Target target = CollectionMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isEmpty();
        assertThat( target.getPublicFooSet() ).isEmpty();

        target.setPublicBarMap( null );

        Source source2 = CollectionMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isEmpty();
        assertThat( source2.publicBarMap ).isEmpty();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setBarMap( null );
        source.publicBarMap = null;

        Target target = CollectionMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getBarMap() ).isEmpty();
        assertThat( target.getPublicBarMap() ).isEmpty();

        target.setPublicBarMap( null );

        Source source2 = CollectionMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isEmpty();
        assertThat( source2.publicBarMap ).isEmpty();
    }

}
