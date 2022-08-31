/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith.deprecated.newer;

import java.lang.reflect.Method;

import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;
import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;
import org.mapstruct.ap.test.annotatewith.deprecated.older.OlderDeprecatedMapperWithClass;
import org.mapstruct.ap.test.annotatewith.deprecated.older.OlderDeprecatedMapperWithMethod;
import org.mapstruct.ap.test.annotatewith.deprecated.older.OlderRepeatDeprecatedMapper;
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
    @WithClasses( {OlderDeprecatedMapperWithMethod.class, CustomMethodOnlyAnnotation.class} )
    public void deprecatedWithMethodCorrectCopy() throws NoSuchMethodException {
        OlderDeprecatedMapperWithMethod mapper = Mappers.getMapper( OlderDeprecatedMapperWithMethod.class );
        Method method = mapper.getClass().getMethod( "map", OlderDeprecatedMapperWithMethod.Source.class );
        Deprecated annotation = method.getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
    }

    @ProcessorTest
    @WithClasses({ NewerDeprecatedMapperWithMethod.class, CustomMethodOnlyAnnotation.class})
    @EnabledForJreRange(min = JRE.JAVA_11)
    public void deprecatedWithMethodCorrectCopyForJdk11() throws NoSuchMethodException {
        NewerDeprecatedMapperWithMethod mapper = Mappers.getMapper( NewerDeprecatedMapperWithMethod.class );
        Method method = mapper.getClass().getMethod( "map", NewerDeprecatedMapperWithMethod.Source.class );
        Deprecated annotation = method.getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.since() ).isEqualTo( "18" );
        assertThat( annotation.forRemoval() ).isEqualTo( false );
    }

    @ProcessorTest
    @WithClasses(OlderDeprecatedMapperWithClass.class)
    public void deprecatedWithClassCorrectCopy() {
        OlderDeprecatedMapperWithClass mapper = Mappers.getMapper( OlderDeprecatedMapperWithClass.class );
        Deprecated annotation = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
    }

    @ProcessorTest
    @WithClasses(NewerDeprecatedMapperWithClass.class)
    @EnabledForJreRange(min = JRE.JAVA_11)
    public void deprecatedWithClassCorrectCopyForJdk11() {
        NewerDeprecatedMapperWithClass mapper = Mappers.getMapper( NewerDeprecatedMapperWithClass.class );
        Deprecated annotation = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( annotation ).isNotNull();
        assertThat( annotation.since() ).isEqualTo( "11" );
    }

    @ProcessorTest
    @WithClasses(OlderRepeatDeprecatedMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = OlderRepeatDeprecatedMapper.class,
                message = "Annotation \"Deprecated\" is already present with the " +
                    "same elements configuration."
            )
        }
    )
    public void deprecatedWithRepeat() {
    }

    @ProcessorTest
    @WithClasses(NewerRepeatDeprecatedMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = NewerRepeatDeprecatedMapper.class,
                message = "Annotation \"Deprecated\" is already present with the " +
                    "same elements configuration."
            )
        }
    )
    @EnabledForJreRange(min = JRE.JAVA_11)
    public void deprecatedWithRepeatForJdk11() {
    }

    @ProcessorTest
    @WithClasses( { NewerRepeatDeprecatedMapperWithParams.class})
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(
                kind = javax.tools.Diagnostic.Kind.WARNING,
                type = NewerRepeatDeprecatedMapperWithParams.class,
                message = "Annotation \"Deprecated\" is already present with the " +
                    "same elements configuration."
            )
        }
    )
    @EnabledForJreRange(min = JRE.JAVA_11)
    public void bothExistPriorityAnnotateWithForJdk11() {
        NewerRepeatDeprecatedMapperWithParams mapper = Mappers.getMapper( NewerRepeatDeprecatedMapperWithParams.class );
        Deprecated deprecated = mapper.getClass().getAnnotation( Deprecated.class );
        assertThat( deprecated ).isNotNull();
        assertThat( deprecated.since() ).isEqualTo( "1.5" );
        assertThat( deprecated.forRemoval() ).isEqualTo( false );
    }
}
