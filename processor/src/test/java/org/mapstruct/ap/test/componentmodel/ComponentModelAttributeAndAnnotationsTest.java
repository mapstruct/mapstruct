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
package org.mapstruct.ap.test.componentmodel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 */
@RunWith(AnnotationProcessorTestRunner.class)
public class ComponentModelAttributeAndAnnotationsTest {
    @Test
    @WithClasses(ComponentModelPrecedenceMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.SUCCEEDED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.WARNING,
            type = ComponentModelPrecedenceMapper.class,
            line = 26,
            messageRegExp = "'componentModel' attribute in @Mapper specifies a different component model " +
                "than the used annotation. Will ignore the annotation and use the component model '.+?'"
        )
    )
    public void testComponentAttributePrecedence() {
        assertThat( ComponentModelPrecedenceMapper.INSTANCE.getClass()
            .getAnnotation( Component.class ) ).isNotNull();
    }

    @Test
    @WithClasses(ErroneousMultipleComponentModelAnnotationsMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = @Diagnostic(
            kind = javax.tools.Diagnostic.Kind.ERROR,
            type = ErroneousMultipleComponentModelAnnotationsMapper.class,
            line = 28,
            messageRegExp = "Multiple component model annotations found. You should only specify one component model."
        )
    )
    public void testMultipleComponentModelAnnotations() {
    }
}
