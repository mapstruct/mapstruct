/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4060;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4060")
public class Issue4060Test {

    @ProcessorTest
    @WithClasses(ErroneousSetToDefaultMapper.class)
    @ExpectedCompilationOutcome(value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousSetToDefaultMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 87,
                message = "LocalDate does not have an accessible parameterless constructor. " +
                    "Either change the nullValuePropertyMappingStrategy or define a defaultValue " +
                    "or a defaultExpression."),
            @Diagnostic(type = ErroneousSetToDefaultMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 90,
                message = "BigDecimal does not have an accessible parameterless constructor. " +
                    "Either change the nullValuePropertyMappingStrategy or define a defaultValue " +
                    "or a defaultExpression."),
            @Diagnostic(type = ErroneousSetToDefaultMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 93,
                message = "Comparable<String> does not have an accessible parameterless constructor. " +
                    "Either change the nullValuePropertyMappingStrategy or define a defaultValue " +
                    "or a defaultExpression."),
            @Diagnostic(type = ErroneousSetToDefaultMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 96,
                message = "ErroneousSetToDefaultMapper.AbstractValue does not have an accessible " +
                    "parameterless constructor. " +
                    "Either change the nullValuePropertyMappingStrategy or define a defaultValue " +
                    "or a defaultExpression."),
            @Diagnostic(type = ErroneousSetToDefaultMapper.class,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                line = 99,
                message = "ErroneousSetToDefaultMapper.EnumValue does not have an accessible " +
                    "parameterless constructor. " +
                    "Either change the nullValuePropertyMappingStrategy or define a defaultValue " +
                    "or a defaultExpression.")
        })
    void setToDefaultWithoutParameterlessConstructorFails() {
    }

    @ProcessorTest
    @WithClasses(SetToDefaultMapper.class)
    void setToDefaultWithParameterlessConstructorResetsToNewInstance() {
        SetToDefaultMapper.Target target = new SetToDefaultMapper.Target();
        SetToDefaultMapper.Nested existing = new SetToDefaultMapper.Nested();
        existing.setName( "existing" );
        target.setNested( existing );
        target.setText( "existing" );

        SetToDefaultMapper.INSTANCE.update( target, new SetToDefaultMapper.Source() );

        // source properties are null, SET_TO_DEFAULT resets them to a fresh default
        assertThat( target.getNested() ).isNotNull();
        assertThat( target.getNested() ).isNotSameAs( existing );
        assertThat( target.getNested().getName() ).isNull();
        assertThat( target.getText() ).isEmpty();
    }
}
