/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith.deprecated.jdk11;

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
    @WithClasses({ DeprecatedMapperWithMethod.class, CustomMethodOnlyAnnotation.class})
    public void deprecatedWithMethodCorrectCopyForJdk11() throws NoSuchMethodException {
        DeprecatedMapperWithMethod mapper = Mappers.getMapper( DeprecatedMapperWithMethod.class );
        Method method = mapper.getClass().getMethod( "map", DeprecatedMapperWithMethod.Source.class );
        Deprecated annotation = method.getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.since() ).isEqualTo( "18" );
        assertThat( annotation.forRemoval() ).isEqualTo( false );
    }

    @ProcessorTest
    @WithClasses(DeprecatedMapperWithClass.class)
    public void deprecatedWithClassCorrectCopyForJdk11() {
        DeprecatedMapperWithClass mapper = Mappers.getMapper( DeprecatedMapperWithClass.class );
        Deprecated annotation = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.since() ).isEqualTo( "11" );
    }

    @ProcessorTest
    @WithClasses( { RepeatDeprecatedMapperWithParams.class})
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = RepeatDeprecatedMapperWithParams.class,
                message = "Annotation \"Deprecated\" is already present with the " +
                    "same elements configuration."
            )
        }
    )
    public void bothExistPriorityAnnotateWithForJdk11() {
        RepeatDeprecatedMapperWithParams mapper = Mappers.getMapper( RepeatDeprecatedMapperWithParams.class );
        Deprecated deprecated = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( deprecated ).isNotNull();
        assertThat( deprecated.since() ).isEqualTo( "1.5" );
        assertThat( deprecated.forRemoval() ).isEqualTo( false );
    }
}
