/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith.deprecated;

import java.lang.reflect.Method;
import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author orange add
 */
public class DeprecatedTest {

    @ProcessorTest
    @WithClasses( { DeprecatedMapperWithMethod.class, CustomMethodOnlyAnnotation.class} )
    public void deprecatedWithMethodCorrectCopy() throws NoSuchMethodException {
        DeprecatedMapperWithMethod mapper = Mappers.getMapper( DeprecatedMapperWithMethod.class );
        Method method = mapper.getClass().getMethod( "map", DeprecatedMapperWithMethod.Source.class );
        Deprecated annotation = method.getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
    }

    @ProcessorTest
    @WithClasses(DeprecatedMapperWithClass.class)
    public void deprecatedWithClassCorrectCopy() {
        DeprecatedMapperWithClass mapper = Mappers.getMapper( DeprecatedMapperWithClass.class );
        Deprecated annotation = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
    }

    @ProcessorTest
    @WithClasses(RepeatDeprecatedMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = RepeatDeprecatedMapper.class,
                message = "Annotation \"Deprecated\" is already present with the " +
                    "same elements configuration."
            )
        }
    )
    public void deprecatedWithRepeat() {
    }
}
