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
@IssueKey("1574")
@WithClasses(AnnotateWithEnum.class)
public class AnnotateWithTest {

    @ProcessorTest
    @WithClasses({ DeprecateAndCustomMapper.class, CustomAnnotation.class })
    public void mapperBecomesDeprecatedAndGetsCustomAnnotation() {
        DeprecateAndCustomMapper mapper = Mappers.getMapper( DeprecateAndCustomMapper.class );

        assertThat( mapper.getClass() ).hasAnnotations( Deprecated.class, CustomAnnotation.class );
    }

    @ProcessorTest
    @WithClasses({ CustomNamedMapper.class, CustomAnnotationWithParams.class })
    public void annotationWithValue() {
        CustomNamedMapper mapper = Mappers.getMapper( CustomNamedMapper.class );

        CustomAnnotationWithParams annotation = mapper.getClass().getAnnotation( CustomAnnotationWithParams.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.booleanParam() ).isEqualTo( true );
        assertThat( annotation.byteParam() ).isEqualTo( (byte) 0x13 );
        assertThat( annotation.charParam() ).isEqualTo( 'a' );
        assertThat( annotation.doubleParam() ).isEqualTo( 1.2 );
        assertThat( annotation.floatParam() ).isEqualTo( 1.2f );
        assertThat( annotation.intParam() ).isEqualTo( 1 );
        assertThat( annotation.longParam() ).isEqualTo( 1L );
        assertThat( annotation.shortParam() ).isEqualTo( (short) 1 );
        assertThat( annotation.stringParam() ).isEqualTo( "test" );
        assertThat( annotation.booleanArray() ).containsExactly( true );
        assertThat( annotation.byteArray() ).containsExactly( (byte) 0x10 );
        assertThat( annotation.charArray() ).containsExactly( 'd' );
        assertThat( annotation.doubleArray() ).containsExactly( 0.3 );
        assertThat( annotation.floatArray() ).containsExactly( 0.3f );
        assertThat( annotation.intArray() ).containsExactly( 3 );
        assertThat( annotation.longArray() ).containsExactly( 3L );
        assertThat( annotation.shortArray() ).containsExactly( (short) 3 );
        assertThat( annotation.stringArray() ).containsExactly( "test" );
        assertThat( annotation.genericTypedClass() ).isEqualTo( CustomAnnotationWithParams.class );
    }

    @ProcessorTest
    @WithClasses({ MultipleArrayValuesMapper.class, CustomAnnotationWithParams.class })
    public void annotationWithMultipleValues() {
        MultipleArrayValuesMapper mapper = Mappers.getMapper( MultipleArrayValuesMapper.class );

        CustomAnnotationWithParams annotation = mapper.getClass().getAnnotation( CustomAnnotationWithParams.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.booleanArray() ).containsExactly( false, true );
        assertThat( annotation.byteArray() ).containsExactly( (byte) 0x08, (byte) 0x1f );
        assertThat( annotation.charArray() ).containsExactly( 'b', 'c' );
        assertThat( annotation.doubleArray() ).containsExactly( 1.2, 3.4 );
        assertThat( annotation.floatArray() ).containsExactly( 1.2f, 3.4f );
        assertThat( annotation.intArray() ).containsExactly( 12, 34 );
        assertThat( annotation.longArray() ).containsExactly( 12L, 34L );
        assertThat( annotation.shortArray() ).containsExactly( (short) 12, (short) 34 );
        assertThat( annotation.stringArray() ).containsExactly( "test1", "test2" );
        assertThat( annotation.classArray() ).containsExactly( Mapper.class, CustomAnnotationWithParams.class );
    }

    @ProcessorTest
    @WithClasses({ CustomNamedGenericClassMapper.class, CustomAnnotationWithParams.class })
    public void annotationWithCorrectGenericClassValue() {
        CustomNamedGenericClassMapper mapper = Mappers.getMapper( CustomNamedGenericClassMapper.class );

        CustomAnnotationWithParams annotation = mapper.getClass().getAnnotation( CustomAnnotationWithParams.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.stringParam() ).isEqualTo( "test" );
        assertThat( annotation.genericTypedClass() ).isEqualTo( Mapper.class );
    }

    @ProcessorTest
    @WithClasses({ MetaAnnotatedMapper.class, ClassMetaAnnotation.class, CustomClassOnlyAnnotation.class })
    public void metaAnnotationWorks() {
        MetaAnnotatedMapper mapper = Mappers.getMapper( MetaAnnotatedMapper.class );

        assertThat( mapper.getClass() ).hasAnnotation( CustomClassOnlyAnnotation.class );
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMissingParameter.class,
                line = 16,
                message = "Parameter \"required\" is required for annotation \"AnnotationWithRequiredParameter\"."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithMissingParameter.class, AnnotationWithRequiredParameter.class })
    public void erroneousMapperWithMissingParameter() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMethodOnInterface.class,
                line = 15,
                message = "Annotation \"CustomMethodOnlyAnnotation\" is not allowed on classes."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithMethodOnInterface.class, CustomMethodOnlyAnnotation.class })
    public void erroneousMapperWithMethodOnInterface() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMethodOnClass.class,
                line = 15,
                message = "Annotation \"CustomMethodOnlyAnnotation\" is not allowed on classes."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithMethodOnClass.class, CustomMethodOnlyAnnotation.class })
    public void erroneousMapperWithMethodOnClass() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithAnnotationOnlyOnInterface.class,
                line = 15,
                message = "Annotation \"CustomAnnotationOnlyAnnotation\" is not allowed on classes."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithAnnotationOnlyOnInterface.class, CustomAnnotationOnlyAnnotation.class })
    public void erroneousMapperWithAnnotationOnlyOnInterface() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithAnnotationOnlyOnClass.class,
                line = 15,
                message = "Annotation \"CustomAnnotationOnlyAnnotation\" is not allowed on classes."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithAnnotationOnlyOnClass.class, CustomAnnotationOnlyAnnotation.class })
    public void erroneousMapperWithAnnotationOnlyOnClass() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithClassOnMethod.class,
                line = 17,
                message = "Annotation \"CustomClassOnlyAnnotation\" is not allowed on methods."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithClassOnMethod.class, CustomClassOnlyAnnotation.class })
    public void erroneousMapperWithClassOnMethod() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithUnknownParameter.class,
                line = 17,
                message = "Parameter \"unknownParameter\" is not present in annotation \"CustomAnnotation\"."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithUnknownParameter.class, CustomAnnotation.class })
    public void erroneousMapperWithUnknownParameter() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithNonExistantEnum.class,
                line = 17,
                message = "Enum \"AnnotateWithEnum\" does not have value \"NON_EXISTANT\"."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithNonExistantEnum.class, CustomAnnotationWithParams.class })
    public void erroneousMapperWithNonExistantEnum() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 17,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"boolean\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 18,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"byte\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 19,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"char\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 20,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"CustomAnnotationWithParams\""
                    + " but of type \"String\" for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 21,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"double\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 22,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"float\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 23,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"int\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 24,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"long\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 25,
                alternativeLine = 33,
                message = "Parameter \"stringParam\" is not of type \"short\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 27,
                alternativeLine = 33,
                message = "Parameter \"genericTypedClass\" is not of type \"String\" "
                    + "but of type \"Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 28,
                alternativeLine = 33,
                message = "Parameter \"genericTypedClass\" is not of type \"ErroneousMapperWithWrongParameter\" "
                    + "but of type \"Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 29,
                alternativeLine = 33,
                message = "Parameter \"enumParam\" is not of type \"WrongAnnotateWithEnum\" "
                    + "but of type \"AnnotateWithEnum\" for annotation \"CustomAnnotationWithParams\"."
            )
        }
    )
    @WithClasses({
        ErroneousMapperWithWrongParameter.class, CustomAnnotationWithParams.class,
        WrongAnnotateWithEnum.class
    })
    public void erroneousMapperWithWrongParameter() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 17,
                alternativeLine = 32,
                message = "Parameter \"stringParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 18,
                alternativeLine = 32,
                message = "Parameter \"booleanParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 19,
                alternativeLine = 32,
                message = "Parameter \"byteParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 20,
                alternativeLine = 32,
                message = "Parameter \"charParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 21,
                alternativeLine = 32,
                message = "Parameter \"doubleParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 22,
                alternativeLine = 32,
                message = "Parameter \"floatParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 23,
                alternativeLine = 32,
                message = "Parameter \"intParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 24,
                alternativeLine = 32,
                message = "Parameter \"longParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 25,
                alternativeLine = 32,
                message = "Parameter \"shortParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 26,
                alternativeLine = 32,
                message = "Parameter \"genericTypedClass\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 27,
                alternativeLine = 32,
                message = "Parameter \"enumParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            )
        }
    )
    @WithClasses({ ErroneousMultipleArrayValuesMapper.class, CustomAnnotationWithParams.class })
    public void erroneousMapperUsingMultipleValuesInsteadOfSingle() {
    }
}
