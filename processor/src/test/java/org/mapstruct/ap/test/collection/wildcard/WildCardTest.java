/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/527.
 *
 * @author Sjaak Derksen
 */
@IssueKey("527")
@RunWith(AnnotationProcessorTestRunner.class)
public class WildCardTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

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
        generatedSource.forMapper( ExtendsBoundSourceTargetMapper.class )
            .content()
            .as( "Should not contain FQN after extends" )
            .doesNotContain( "? extends org.mapstruct.ap.test.collection.wildcard.Idea" );
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
        generatedSource.forMapper( SourceSuperBoundTargetMapper.class )
            .content()
            .as( "Should not contain FQN after super" )
            .doesNotContain( "? super org.mapstruct.ap.test.collection.wildcard.Idea" );
    }

    @Test
    @WithClasses({ ErroneousIterableSuperBoundSourceMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ErroneousIterableSuperBoundSourceMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 20,
                        messageRegExp = "Can't generate mapping method for a wildcard super bound source." )
            }
    )
    public void shouldFailOnSuperBoundSource() {
    }

    @Test
    @WithClasses({ ErroneousIterableExtendsBoundTargetMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ErroneousIterableExtendsBoundTargetMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 20,
                        messageRegExp = "Can't generate mapping method for a wildcard extends bound result." )
            }
    )
    public void shouldFailOnExtendsBoundTarget() {
    }

   @Test
    @WithClasses({ ErroneousIterableTypeVarBoundMapperOnMethod.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
            @Diagnostic(type = ErroneousIterableTypeVarBoundMapperOnMethod.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 20,
                        messageRegExp = "Can't generate mapping method for a generic type variable target." )
            }
    )
    public void shouldFailOnTypeVarSource() {
    }

    @Test
    @WithClasses({ ErroneousIterableTypeVarBoundMapperOnMapper.class })
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                @Diagnostic( type = ErroneousIterableTypeVarBoundMapperOnMapper.class,
                        kind = javax.tools.Diagnostic.Kind.ERROR,
                        line = 20,
                        messageRegExp = "Can't generate mapping method for a generic type variable source." )
            }
    )
    public void shouldFailOnTypeVarTarget() {
    }

    @Test
    @WithClasses( { BeanMapper.class, GoodIdea.class, CunningPlan.class } )
    public void shouldMapBean() {

        GoodIdea aGoodIdea = new GoodIdea();
        aGoodIdea.setContent( new JAXBElement<BigDecimal>( new QName( "test" ), BigDecimal.class, BigDecimal.ONE ) );
        aGoodIdea.setDescription( BigDecimal.ZERO );

        CunningPlan aCunningPlan = BeanMapper.STM.transformA( aGoodIdea );

        assertThat( aCunningPlan ).isNotNull();
        assertThat( aCunningPlan.getContent() ).isEqualTo( BigDecimal.ONE );
        assertThat( aCunningPlan.getDescription() ).isNotNull();
        assertThat( aCunningPlan.getDescription().getValue() ).isEqualTo( BigDecimal.ZERO );
    }

}
