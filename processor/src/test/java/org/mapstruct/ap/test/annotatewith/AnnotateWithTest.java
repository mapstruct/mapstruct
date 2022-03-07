/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.Mapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ben Zegveld
 */
@IssueKey( "1574" )
public class AnnotateWithTest {

    @ProcessorTest
    @WithClasses( { DeprecateMapper.class } )
    public void mapperBecomesDeprecated() {
        DeprecateMapper mapper = Mappers.getMapper( DeprecateMapper.class );

        assertThat( mapper.getClass().getAnnotation( Deprecated.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( { DeprecateAndCustomMapper.class, CustomAnnotation.class } )
    public void mapperBecomesDeprecatedAndGetsCustomAnnotation() {
        DeprecateAndCustomMapper mapper = Mappers.getMapper( DeprecateAndCustomMapper.class );

        assertThat( mapper.getClass().getAnnotation( Deprecated.class ) ).isNotNull();
        assertThat( mapper.getClass().getAnnotation( CustomAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( { CustomNamedMapper.class, CustomNamed.class } )
    public void annotationWithValue() {
        CustomNamedMapper mapper = Mappers.getMapper( CustomNamedMapper.class );

        CustomNamed annotation = mapper.getClass().getAnnotation( CustomNamed.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "test" );
        assertThat( annotation.genericTypedClass() ).isEqualTo( CustomNamed.class );
    }

    @ProcessorTest
    @WithClasses( { CustomNamedGenericClassMapper.class, CustomNamed.class } )
    public void annotationWithcorrectGenericClassValue() {
        CustomNamedGenericClassMapper mapper = Mappers.getMapper( CustomNamedGenericClassMapper.class );

        CustomNamed annotation = mapper.getClass().getAnnotation( CustomNamed.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.value() ).isEqualTo( "test" );
        assertThat( annotation.genericTypedClass() ).isEqualTo( Mapper.class );
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMissingParameter.class,
                line = 16,
                message = "Parameter \"required\" is required for annotation "
                    + "\"org.mapstruct.ap.test.annotatewith.AnnotationWithRequiredParameter\"."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithMissingParameter.class, AnnotationWithRequiredParameter.class } )
    public void erroneousMapperWithMissingParameter() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithUnknownParameter.class,
                line = 18,
                message = "Parameter \"unknownParameter\" is not present in annotation "
                    + "\"org.mapstruct.ap.test.annotatewith.CustomAnnotation\"."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithUnknownParameter.class, CustomAnnotation.class } )
    public void erroneousMapperWithUnknownParameter() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 16,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"boolean\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 19,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"byte\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 22,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"char\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 25,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"org.mapstruct.ap.test.annotatewith.CustomNamed\""
                    + " but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 28,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"double\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 31,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"float\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 34,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"int\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 37,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"long\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 40,
                alternativeLine = 55,
                message = "Parameter \"value\" is not of type \"short\" but of type \"java.lang.String\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 45,
                alternativeLine = 55,
                message = "Parameter \"genericTypedClass\" is not of type \"java.lang.String\" "
                    + "but of type \"java.lang.Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 51,
                alternativeLine = 55,
                message = "Parameter \"genericTypedClass\" is not of type "
                    + "\"org.mapstruct.ap.test.annotatewith.ErroneousMapperWithWrongParameter\" "
                    + "but of type \"java.lang.Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"org.mapstruct.ap.test.annotatewith.CustomNamed\"."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithWrongParameter.class, CustomNamed.class } )
    public void erroneousMapperWithWrongParameter() {
    }
}
