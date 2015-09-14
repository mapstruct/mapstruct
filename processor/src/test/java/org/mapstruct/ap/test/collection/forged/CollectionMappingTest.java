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
package org.mapstruct.ap.test.collection.forged;


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

import static org.fest.assertions.Assertions.assertThat;

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
        source.setFooSet( Collections.asSet( "1", "2") );

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isEqualTo( Collections.asSet( 1L, 2L ) );

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isEqualTo( Collections.asSet( "1", "2" ) );
    }

    @Test
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethod() {

        Map<String, Long> sourceMap = ImmutableMap.<String, Long>builder().put( "rabbit", 1L ).build();
        Source source = new Source();
        source.setBarMap( sourceMap );

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        Map<String, String> targetMap = ImmutableMap.<String, String>builder().put( "rabbit", "1" ).build();
        assertThat( target.getBarMap() ).isEqualTo( targetMap );

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isEqualTo( sourceMap );
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
                messageRegExp = "Can't map property \".* nonMappableSet\" to \".* nonMappableSet\". "
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
                messageRegExp = "Can't map property \".* nonMappableMap\" to \".* nonMappableMap\". "
                    + "Consider to declare/implement a mapping method: .*."),
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

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isNull();

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isNull();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapper.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethodReturnNullOnNullSource() {

        Source source = new Source();
        source.setBarMap( null );

        Target target = CollectionMapper.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getBarMap() ).isNull();

        Source source2 = CollectionMapper.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isNull();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewIterableMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setFooSet( null );

        Target target = CollectionMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).isEmpty();

        Source source2 = CollectionMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getFooSet() ).isEmpty();
    }

    @Test
    @IssueKey( "640" )
    @WithClasses({ CollectionMapperNullValueMappingReturnDefault.class, Source.class, Target.class })
    public void shouldForgeNewMapMappingMethodReturnEmptyOnNullSource() {

        Source source = new Source();
        source.setBarMap( null );

        Target target = CollectionMapperNullValueMappingReturnDefault.INSTANCE.sourceToTarget( source );
        assertThat( target ).isNotNull();
        assertThat( target.getBarMap() ).isEmpty();

        Source source2 = CollectionMapperNullValueMappingReturnDefault.INSTANCE.targetToSource( target );
        assertThat( source2 ).isNotNull();
        assertThat( source2.getBarMap() ).isEmpty();
    }

}
