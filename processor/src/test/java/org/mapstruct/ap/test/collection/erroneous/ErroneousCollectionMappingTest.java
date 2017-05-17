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
package org.mapstruct.ap.test.collection.erroneous;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.NoProperties;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for illegal mappings between collection types, iterable and non-iterable types etc.
 *
 * @author Gunnar Morling
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class ErroneousCollectionMappingTest {

    @Test
    @IssueKey("6")
    @WithClasses({ ErroneousCollectionToNonCollectionMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionToNonCollectionMapper.class,
                kind = Kind.ERROR,
                line = 28,
                messageRegExp = "Can't generate mapping method from iterable type to non-iterable type"),
            @Diagnostic(type = ErroneousCollectionToNonCollectionMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = "Can't generate mapping method from non-iterable type to iterable type")
        }
    )
    public void shouldFailToGenerateImplementationBetweenCollectionAndNonCollection() {
    }

    @Test
    @IssueKey("729")
    @WithClasses({ ErroneousCollectionToPrimitivePropertyMapper.class, Source.class, Target.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionToPrimitivePropertyMapper.class,
                kind = Kind.ERROR,
                line = 26,
                messageRegExp = "Can't map property \"java.util.List<java.lang.String> strings\" to \"int strings\". "
                    + "Consider to declare/implement a mapping method: \"int map\\(java.util.List<java.lang.String>"
                    + " value\\)\"")
        }
    )
    public void shouldFailToGenerateImplementationBetweenCollectionAndPrimitive() {
    }

    @Test
    @IssueKey("417")
    @WithClasses({ EmptyItererableMappingMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyItererableMappingMapper.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp = "'nullValueMappingStrategy','dateformat', 'qualifiedBy' and 'elementTargetType' are "
                    + "undefined in @IterableMapping, define at least one of them.")
        }
    )
    public void shouldFailOnEmptyIterableAnnotation() {
    }

    @Test
    @IssueKey("417")
    @WithClasses({ EmptyMapMappingMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = EmptyMapMappingMapper.class,
                kind = Kind.ERROR,
                line = 35,
                messageRegExp = "'nullValueMappingStrategy', 'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', "
                    + "'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, "
                    + "define at least one of them.")
        }
    )
    public void shouldFailOnEmptyMapAnnotation() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoElementMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Can't map Collection element \".*WithProperties withProperties\" to \".*NoProperties" +
                    " noProperties\". Consider to declare/implement a mapping method: \".*NoProperties map\\(" +
                    ".*WithProperties value\\)")
        }
    )
    public void shouldFailOnNoElementMappingFound() {
    }

    @Test
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoElementMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoElementMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 32,
                messageRegExp =
                    "Can't map collection element \".*AttributedString\" to \".*String \". " +
                        "Consider to declare/implement a mapping method: \".*String map\\(.*AttributedString value\\)")
        }
    )
    public void shouldFailOnNoElementMappingFoundWithDisabledAuto() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoKeyMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoKeyMappingFound.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Can't map Map key \".*WithProperties withProperties\" to \".*NoProperties " +
                    "noProperties\". Consider to declare/implement a mapping method: \".*NoProperties map\\(" +
                    ".*WithProperties value\\)")
        }
    )
    public void shouldFailOnNoKeyMappingFound() {
    }

    @Test
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoKeyMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoKeyMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 32,
                messageRegExp = "Can't map map key \".*AttributedString\" to \".*String \". " +
                    "Consider to declare/implement a mapping method: \".*String map\\(.*AttributedString value\\)")
        }
    )
    public void shouldFailOnNoKeyMappingFoundWithDisabledAuto() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoValueMappingFound.class, NoProperties.class, WithProperties.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoValueMappingFound.class,
                kind = Kind.ERROR,
                line = 38,
                messageRegExp = "Can't map Map value \".*WithProperties withProperties\" to \".*NoProperties " +
                    "noProperties\". Consider to declare/implement a mapping method: \".*NoProperties map\\(" +
                    ".*WithProperties value\\)")
        }
    )
    public void shouldFailOnNoValueMappingFound() {
    }

    @Test
    @IssueKey("993")
    @WithClasses({ ErroneousCollectionNoValueMappingFoundDisabledAuto.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoValueMappingFoundDisabledAuto.class,
                kind = Kind.ERROR,
                line = 32,
                messageRegExp = "Can't map map value \".*AttributedString\" to \".*String \". " +
                    "Consider to declare/implement a mapping method: \".*String map(.*AttributedString value)")
        }
    )
    public void shouldFailOnNoValueMappingFoundWithDisabledAuto() {
    }
}
