/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Test for illegal mappings between collection/stream types, iterable and non-iterable types etc.
 *
 * Currently org.mapstruct.ap.test.stream.erroneous.ErroneousStreamMappingTest#shouldFailOnNoElementMappingFound
 * and org.mapstruct.ap.test.stream.erroneous
 * .ErroneousStreamMappingTest#shouldFailOnEmptyIterableAnnotationStreamMappings
 * fail with the eclipse compiler, because when the result type is a Stream also the Message.GENERAL_NO_IMPLEMENTATION
 * is reported, because Stream is an Interface. However, maybe if the resultType is stream when we
 * do StreamMethodMapping in the MapperCreationProcessor we need to say that there is a factory method
 *
 * @author Filip Hrisafov
 */
@IssueKey("962")
public class ErroneousStreamMappingTest {

    @ProcessorTest
    @WithClasses({ ErroneousStreamToNonStreamMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToNonStreamMapper.class,
                kind = Kind.ERROR,
                line = 15,
                message = "Can't generate mapping method from iterable type from java stdlib to non-iterable type."),
            @Diagnostic(type = ErroneousStreamToNonStreamMapper.class,
                kind = Kind.ERROR,
                line = 17,
                message = "Can't generate mapping method from non-iterable type to iterable type from java stdlib.")
        }
    )
    public void shouldFailToGenerateImplementationBetweenStreamAndNonStreamOrIterable() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousStreamToPrimitivePropertyMapper.class, Source.class, Target.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToPrimitivePropertyMapper.class,
                kind = Kind.ERROR,
                line = 13,
                message = "Can't map property \"Stream<String> strings\" to \"int strings\". " +
                    "Consider to declare/implement a mapping method: \"int map(Stream<String> value)\".")
        }
    )
    public void shouldFailToGenerateImplementationBetweenCollectionAndPrimitive() {
    }

    @ProcessorTest
    @WithClasses({ EmptyStreamMappingMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyStreamMappingMapper.class,
                kind = Kind.ERROR,
                line = 23,
                message = "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are "
                    + "undefined in @IterableMapping, define at least one of them."),
            @Diagnostic(type = EmptyStreamMappingMapper.class,
                kind = Kind.ERROR,
                line = 26,
                message = "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are "
                    + "undefined in @IterableMapping, define at least one of them."),
            @Diagnostic(type = EmptyStreamMappingMapper.class,
                kind = Kind.ERROR,
                line = 29,
                message = "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are "
                    + "undefined in @IterableMapping, define at least one of them.")
        }
    )
    public void shouldFailOnEmptyIterableAnnotationStreamMappings() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousStreamToStreamNoElementMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToStreamNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 24,
                message = "No target bean properties found: " +
                    "can't map Stream element \"WithProperties withProperties\" to \"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForStreamToStream() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousStreamToStreamNoElementMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToStreamNoElementMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Can't map stream element \"AttributedString\" to \"String \". " +
                    "Consider to declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForStreamToStreamWithDisabledAuto() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousListToStreamNoElementMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousListToStreamNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 25,
                message = "No target bean properties found: " +
                    "can't map Stream element \"WithProperties withProperties\" to \"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForListToStream() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousListToStreamNoElementMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousListToStreamNoElementMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Can't map stream element \"AttributedString\" to \"String \". " +
                    "Consider to declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForListToStreamWithDisabledAuto() {
    }

    @ProcessorTest
    @WithClasses({ ErroneousStreamToListNoElementMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToListNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 25,
                message = "No target bean properties found: " +
                    "can't map Stream element \"WithProperties withProperties\" to \"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForStreamToList() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousStreamToListNoElementMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStreamToListNoElementMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Can't map stream element \"AttributedString\" to \"String \". " +
                    "Consider to declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundForStreamToListWithDisabledAuto() {
    }
}
