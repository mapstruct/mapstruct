/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.testutil.compilation.annotation.ExpectedCompilationOutcome;

/**
 * Test for illegal mappings between collection types, iterable and non-iterable types etc.
 *
 * @author Gunnar Morling
 */
public class ErroneousCollectionMappingTest {

    @ProcessorTest
    @IssueKey("6")
    @WithClasses({ ErroneousCollectionToNonCollectionMapper.class, Source.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionToNonCollectionMapper.class,
                kind = Kind.ERROR,
                line = 15,
                message = "Can't generate mapping method from iterable type from java stdlib to non-iterable type."),
            @Diagnostic(type = ErroneousCollectionToNonCollectionMapper.class,
                kind = Kind.ERROR,
                line = 17,
                message = "Can't generate mapping method from non-iterable type to iterable type from java stdlib."),
            @Diagnostic(type = ErroneousCollectionToNonCollectionMapper.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Can't generate mapping method from non-iterable type to iterable type from java stdlib.")
        }
    )
    public void shouldFailToGenerateImplementationBetweenCollectionAndNonCollectionWithResultTypeFromJava() {
    }

    @ProcessorTest
    @IssueKey("729")
    @WithClasses({ ErroneousCollectionToPrimitivePropertyMapper.class, Source.class, Target.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionToPrimitivePropertyMapper.class,
                kind = Kind.ERROR,
                line = 13,
                message = "Can't map property \"List<String> strings\" to \"int strings\". "
                    + "Consider to declare/implement a mapping method: \"int map(List<String> value)\".")
        }
    )
    public void shouldFailToGenerateImplementationBetweenCollectionAndPrimitive() {
    }

    @ProcessorTest
    @IssueKey("417")
    @WithClasses({ EmptyItererableMappingMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyItererableMappingMapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are "
                    + "undefined in @IterableMapping, define at least one of them.")
        }
    )
    public void shouldFailOnEmptyIterableAnnotation() {
    }

    @ProcessorTest
    @IssueKey("417")
    @WithClasses({ EmptyMapMappingMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyMapMappingMapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "'nullValueMappingStrategy', 'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', "
                    + "'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, "
                    + "define at least one of them.")
        }
    )
    public void shouldFailOnEmptyMapAnnotation() {
    }

    @ProcessorTest
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoElementMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 25,
                message = "No target bean properties found: can't map Collection element " +
                    "\"WithProperties withProperties\" to \"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFound() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoElementMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoElementMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Can't map collection element \"AttributedString\" to \"String \". " +
                    "Consider to declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoElementMappingFoundWithDisabledAuto() {
    }

    @ProcessorTest
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoKeyMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoKeyMappingFound.class,
                kind = Kind.ERROR,
                line = 25,
                message = "No target bean properties found: can't map Map key \"WithProperties withProperties\" to " +
                    "\"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoKeyMappingFound() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoKeyMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoKeyMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Can't map map key \"AttributedString\" to \"String \". Consider to " +
                    "declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoKeyMappingFoundWithDisabledAuto() {
    }

    @ProcessorTest
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoValueMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoValueMappingFound.class,
                kind = Kind.ERROR,
                line = 25,
                message = "No target bean properties found: can't map Map value \"WithProperties withProperties\" " +
                    "to \"NoProperties noProperties\". " +
                    "Consider to declare/implement a mapping method: \"NoProperties map(WithProperties value)\".")
        }
    )
    public void shouldFailOnNoValueMappingFound() {
    }

    @ProcessorTest
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoValueMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoValueMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 19,
                message = "Can't map map value \"AttributedString\" to \"String \". " +
                    "Consider to declare/implement a mapping method: \"String map(AttributedString value)\".")
        }
    )
    public void shouldFailOnNoValueMappingFoundWithDisabledAuto() {
    }
}
