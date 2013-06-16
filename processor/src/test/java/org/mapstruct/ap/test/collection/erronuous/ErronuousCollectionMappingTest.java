/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.collection.erronuous;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.testng.annotations.Test;

/**
 * Test for illegal mappings between iterable and non-iterable types.
 *
 * @author Gunnar Morling
 */
@WithClasses({ ErronuousMapper.class })
public class ErronuousCollectionMappingTest extends MapperTestBase {

    @Test
    @IssueKey("6")
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErronuousMapper.class,
                kind = Kind.ERROR,
                line = 28,
                messageRegExp = ".*Can't generate mapping method from iterable type to non-iterable type\\."),
            @Diagnostic(type = ErronuousMapper.class,
                kind = Kind.ERROR,
                line = 30,
                messageRegExp = ".*Can't generate mapping method from non-iterable type to iterable type\\.")
        }
    )
    public void shouldFailToGenerateMappingFromListToString() {
    }
}
