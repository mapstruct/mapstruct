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
package org.mapstruct.ap.test.collection.erroneous;

import javax.tools.Diagnostic.Kind;

import org.junit.Test;
import org.junit.runner.RunWith;
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
                line = 34,
                messageRegExp = "'nullValueMappingStrategy', 'keyDateFormat', 'keyQualifiedBy', 'keyTargetType', "
                    + "'valueDateFormat', 'valueQualfiedBy' and 'valueTargetType' are all undefined in @MapMapping, "
                    + "define at least one of them.")
        }
    )
    public void shouldFailOnEmptyMapAnnotation() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoElementMappingFound.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoElementMappingFound.class,
                kind = Kind.ERROR,
                line = 36,
                messageRegExp = "No implementation can be generated for this method. Found no method nor implicit "
                    + "conversion for mapping source element type into target element type.")
        }
    )
    public void shouldFailOnNoElementMappingFound() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoKeyMappingFound.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoKeyMappingFound.class,
                kind = Kind.ERROR,
                line = 36,
                messageRegExp = "No implementation can be generated for this method. Found no method nor implicit "
                    + "conversion for mapping source key type to target key type.")
        }
    )
    public void shouldFailOnNoKeyMappingFound() {
    }

    @Test
    @IssueKey("459")
    @WithClasses({ ErroneousCollectionNoValueMappingFound.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousCollectionNoValueMappingFound.class,
                kind = Kind.ERROR,
                line = 36,
                messageRegExp = "No implementation can be generated for this method. Found no method nor implicit "
                    + "conversion for mapping source value type to target value type.")
        }
    )
    public void shouldFailOnNoValueMappingFound() {
    }
}
