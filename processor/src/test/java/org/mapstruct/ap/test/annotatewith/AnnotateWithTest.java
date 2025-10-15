/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ben Zegveld
 */
@IssueKey("1574")
@WithClasses(AnnotateWithEnum.class)
public class AnnotateWithTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({ DeprecateAndCustomMapper.class, CustomAnnotation.class })
    public void mapperBecomesDeprecatedAndGetsCustomAnnotation() {
        DeprecateAndCustomMapper mapper = Mappers.getMapper( DeprecateAndCustomMapper.class );

        assertThat( mapper.getClass() ).hasAnnotations( Deprecated.class, CustomAnnotation.class );
    }

    @ProcessorTest
    @WithClasses( {
        CustomNamedMapper.class,
        CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class,
        CustomClassOnlyAnnotation.class,
        CustomMethodOnlyAnnotation.class,
    } )
    public void annotationWithValue() {
        generatedSource.addComparisonToFixtureFor( CustomNamedMapper.class );
    }

    @ProcessorTest
    @WithClasses( { MultipleArrayValuesMapper.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void annotationWithMultipleValues() {
        generatedSource.addComparisonToFixtureFor( MultipleArrayValuesMapper.class );
    }

    @ProcessorTest
    @WithClasses( { CustomNamedGenericClassMapper.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void annotationWithCorrectGenericClassValue() {
        CustomNamedGenericClassMapper mapper = Mappers.getMapper( CustomNamedGenericClassMapper.class );

        CustomAnnotationWithParams annotation = mapper.getClass().getAnnotation( CustomAnnotationWithParams.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.stringParam() ).isEqualTo( "test" );
        assertThat( annotation.genericTypedClass() ).isEqualTo( Mapper.class );
    }

    @ProcessorTest
    @WithClasses( { AnnotationWithoutElementNameMapper.class, CustomAnnotation.class } )
    public void annotateWithoutElementName() {
        generatedSource
                       .forMapper( AnnotationWithoutElementNameMapper.class )
                       .content()
                       .contains( "@CustomAnnotation(value = \"value\")" );
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
                line = 15,
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
                line = 18,
                message = "Annotation \"CustomClassOnlyAnnotation\" is not allowed on methods."
            )
        }
    )
    @WithClasses({ ErroneousMapperWithClassOnMethod.class, CustomClassOnlyAnnotation.class, WithProperties.class })
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
                message = "Unknown parameter \"unknownParameter\" for annotation \"CustomAnnotation\"." +
                    " Did you mean \"value\"?"
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
    @WithClasses( { ErroneousMapperWithNonExistantEnum.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperWithNonExistantEnum() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithTooManyParameterValues.class,
                line = 17,
                message = "Parameter \"stringParam\" has too many value types supplied, type \"String\" is expected"
                    + " for annotation \"CustomAnnotationWithParams\"."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithTooManyParameterValues.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperWithTooManyParameterValues() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 16,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"boolean\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 18,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"byte\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 20,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"char\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 22,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"CustomAnnotationWithParams\""
                    + " but of type \"String\" for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 24,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"double\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 26,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"float\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 28,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"int\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 30,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"long\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 32,
                alternativeLine = 43,
                message = "Parameter \"stringParam\" is not of type \"short\" but of type \"String\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 35,
                alternativeLine = 43,
                message = "Parameter \"genericTypedClass\" is not of type \"String\" "
                    + "but of type \"Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 36,
                alternativeLine = 43,
                message = "Parameter \"enumParam\" is not of type \"WrongAnnotateWithEnum\" "
                    + "but of type \"AnnotateWithEnum\" for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 40,
                alternativeLine = 43,
                message = "Parameter \"genericTypedClass\" is not of type \"ErroneousMapperWithWrongParameter\" "
                    + "but of type \"Class<? extends java.lang.annotation.Annotation>\" "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithWrongParameter.class,
                line = 42,
                alternativeLine = 43,
                message = "Parameter \"value\" is not of type \"boolean\" "
                    + "but of type \"String\" for annotation \"CustomAnnotation\"."
            )
        }
    )
    @WithClasses({
        ErroneousMapperWithWrongParameter.class, CustomAnnotationWithParams.class,
        CustomAnnotationWithParamsContainer.class, WrongAnnotateWithEnum.class, CustomAnnotation.class
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
                alternativeLine = 43,
                message = "Parameter \"stringParam\" does not accept multiple values "
                    + "for annotation \"CustomAnnotationWithParams\"."
            ),
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMultipleArrayValuesMapper.class,
                line = 18,
                alternativeLine = 43,
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
    @WithClasses( { ErroneousMultipleArrayValuesMapper.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperUsingMultipleValuesInsteadOfSingle() {
    }

    @ProcessorTest
    @WithClasses( { MapperWithMissingAnnotationElementName.class,
        CustomAnnotationWithTwoAnnotationElements.class } )
    public void mapperWithMissingAnnotationElementNameShouldCompile() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMissingEnumClass.class,
                line = 17,
                message = "enumClass needs to be defined when using enums."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithMissingEnumClass.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperWithMissingEnumClass() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithMissingEnums.class,
                line = 17,
                message = "enums needs to be defined when using enumClass."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithMissingEnums.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperWithMissingEnums() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithRepeatOfNotRepeatableAnnotation.class,
                line = 16,
                alternativeLine = 17,
                message = "Annotation \"CustomAnnotation\" is not repeatable."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithRepeatOfNotRepeatableAnnotation.class, CustomAnnotation.class } )
    public void erroneousMapperWithRepeatOfNotRepeatableAnnotation() {
    }

    @ProcessorTest
    @WithClasses( { MapperWithRepeatableAnnotation.class, CustomRepeatableAnnotation.class,
        CustomRepeatableAnnotationContainer.class } )
    public void mapperWithRepeatableAnnotationShouldCompile() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.ERROR,
                type = ErroneousMapperWithParameterRepeat.class,
                line = 18,
                message = "Parameter \"stringParam\" must not be defined more than once."
            )
        }
    )
    @WithClasses( { ErroneousMapperWithParameterRepeat.class, CustomAnnotationWithParamsContainer.class,
        CustomAnnotationWithParams.class } )
    public void erroneousMapperWithParameterRepeat() {
    }

    @ProcessorTest
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = MapperWithIdenticalAnnotationRepeated.class,
                line = 16,
                alternativeLine = 17,
                message = "Annotation \"CustomRepeatableAnnotation\" is already present "
                    + "with the same elements configuration."
            )
        }
    )
    @WithClasses( { MapperWithIdenticalAnnotationRepeated.class, CustomRepeatableAnnotation.class,
        CustomRepeatableAnnotationContainer.class } )
    public void mapperWithIdenticalAnnotationRepeated() {
    }

    @ProcessorTest
    @WithClasses( {AnnotateBeanMappingMethodMapper.class, CustomMethodOnlyAnnotation.class} )
    public void beanMappingMethodWithCorrectCustomAnnotation() throws NoSuchMethodException {
        AnnotateBeanMappingMethodMapper mapper = Mappers.getMapper( AnnotateBeanMappingMethodMapper.class );
        Method method = mapper.getClass().getMethod( "map", AnnotateBeanMappingMethodMapper.Source.class );
        assertThat( method.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( {AnnotateIterableMappingMethodMapper.class, CustomMethodOnlyAnnotation.class} )
    public void iterableMappingMethodWithCorrectCustomAnnotation() throws NoSuchMethodException {
        AnnotateIterableMappingMethodMapper mapper = Mappers.getMapper( AnnotateIterableMappingMethodMapper.class );
        Method method = mapper.getClass().getMethod( "toStringList", List.class );
        assertThat( method.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( {AnnotateMapMappingMethodMapper.class, CustomMethodOnlyAnnotation.class} )
    public void mapMappingMethodWithCorrectCustomAnnotation() throws NoSuchMethodException {
        AnnotateMapMappingMethodMapper mapper = Mappers.getMapper( AnnotateMapMappingMethodMapper.class );
        Method method = mapper.getClass().getMethod( "longDateMapToStringStringMap", Map.class );
        assertThat( method.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( {AnnotateStreamMappingMethodMapper.class, CustomMethodOnlyAnnotation.class} )
    public void streamMappingMethodWithCorrectCustomAnnotation() throws NoSuchMethodException {
        AnnotateStreamMappingMethodMapper mapper = Mappers.getMapper( AnnotateStreamMappingMethodMapper.class );
        Method method = mapper.getClass().getMethod( "toStringStream", Stream.class );
        assertThat( method.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();
    }

    @ProcessorTest
    @WithClasses( {AnnotateValueMappingMethodMapper.class, AnnotateWithEnum.class, CustomMethodOnlyAnnotation.class} )
    public void valueMappingMethodWithCorrectCustomAnnotation() throws NoSuchMethodException {
        AnnotateValueMappingMethodMapper mapper = Mappers.getMapper( AnnotateValueMappingMethodMapper.class );
        Method method = mapper.getClass().getMethod( "map", String.class );
        assertThat( method.getAnnotation( CustomMethodOnlyAnnotation.class ) ).isNotNull();
    }
}
