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
package org.mapstruct.ap.test.collection.wildcard;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
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
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/527.
 *
 * @author Sjaak Derksen
 */
@IssueKey("527")
@RunWith(AnnotationProcessorTestRunner.class)
public class WildCardTest {

    @Test
    @WithClasses({
        ExtendsBoundSourceTargetMapper.class,
        ExtendsBoundSource.class,
        Target.class,
        Plan.class,
        Idea.class
    })
    public void shouldGenerateExtendsBoundSourceForgedIterableMethod() {

        ExtendsBoundSource source = new ExtendsBoundSource();

        Target target = ExtendsBoundSourceTargetMapper.STM.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getElements() ).isNull();

    }

    @Test
    @WithClasses({
        SourceSuperBoundTargetMapper.class,
        Source.class,
        SuperBoundTarget.class,
        Plan.class,
        Idea.class
    })
    public void shouldGenerateSuperBoundTargetForgedIterableMethod() {

        Source source = new Source();

        SuperBoundTarget target = SourceSuperBoundTargetMapper.STM.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getElements() ).isNull();

    }

    @Test
    @WithClasses({ IterableSuperBoundSourceMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = IterableSuperBoundSourceMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 32,
                        messageRegExp = "Can't generate mapping method for a wildcard super bound source." )
            }
    )
    public void shouldFailOnSuperBoundSource() {
    }

    @Test
    @WithClasses({ IterableExtendsBoundTargetMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = IterableExtendsBoundTargetMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 32,
                        messageRegExp = "Can't generate mapping method for a wildcard extends bound result." )
            }
    )
    public void shouldFailOnExtendsBoundTarget() {
    }

   @Test
    @WithClasses({ IterableTypeVarBoundMapperOnMethod.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = IterableTypeVarBoundMapperOnMethod.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 32,
                        messageRegExp = "Can't generate mapping method for a generic type variable target." )
            }
    )
    public void shouldFailOnTypeVarSource() {
    }

    @Test
    @WithClasses({ IterableTypeVarBoundMapperOnMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = IterableTypeVarBoundMapperOnMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 32,
                        messageRegExp = "Can't generate mapping method for a generic type variable source." )
            }
    )
    public void shouldFailOnTypeVarTarget() {
    }


    @Test
    @WithClasses( { BeanMapper.class, GoodIdea.class, CunningPlan.class } )
    public void shouldMapBean() {

        GoodIdea aGoodIdea = new GoodIdea();
        aGoodIdea.setContent( new JAXBElement( new QName( "test" ), BigDecimal.class, BigDecimal.ONE ) );
        aGoodIdea.setDescription( BigDecimal.ZERO );

        CunningPlan aCunningPlan = BeanMapper.STM.transformA( aGoodIdea );

        assertThat( aCunningPlan ).isNotNull();
        assertThat( aCunningPlan.getContent() ).isEqualTo( BigDecimal.ONE );
        assertThat( aCunningPlan.getDescription() ).isNotNull();
        assertThat( aCunningPlan.getDescription().getValue() ).isEqualTo( BigDecimal.ZERO );
    }

}
