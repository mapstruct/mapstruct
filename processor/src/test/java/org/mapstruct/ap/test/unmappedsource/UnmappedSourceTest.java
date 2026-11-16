/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedsource;

import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.test.unmappedtarget.Source;
import org.mapstruct.ap.test.unmappedtarget.Target;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests expected diagnostics for unmapped source properties.
 *
 * @author Gunnar Morling
 */
public class UnmappedSourceTest {

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, SourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.SUCCEEDED,
        diagnostics = {
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 20,
                message = "Unmapped source property: \"qux\"."),
            @Diagnostic(type = SourceTargetMapper.class,
                kind = Kind.WARNING,
                line = 22,
                message = "Unmapped source property: \"bar\".")
        }
    )
    public void shouldLeaveUnmappedSourcePropertyUnset() {
        Source source = new Source();
        source.setFoo( 42L );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( 42L );
        assertThat( target.getBar() ).isEqualTo( 0 );
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class, ErroneousStrictSourceTargetMapper.class })
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 20,
                message = "Unmapped source property: \"qux\"."),
            @Diagnostic(type = ErroneousStrictSourceTargetMapper.class,
                kind = Kind.ERROR,
                line = 22,
                message = "Unmapped source property: \"bar\".")
        }
    )
    public void shouldRaiseErrorDueToUnsetSourceProperty() {
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class,
            org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "IGNORE")
    @ProcessorOption(name = "mapstruct.unmappedSourcePolicy", value = "ERROR")
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class,
                            kind = Kind.ERROR,
                            line = 22,
                            message = "Unmapped source property: \"qux\"."),
                    @Diagnostic(type = org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class,
                            kind = Kind.ERROR,
                            line = 24,
                            message = "Unmapped source property: \"bar\".")
            }
    )
    public void shouldRaiseErrorDueToUnsetSourcePropertyWithPolicySetViaProcessorOption() {
    }

    @ProcessorTest
    @WithClasses({ Source.class, Target.class,
            org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class })
    @ProcessorOption(name = "mapstruct.unmappedTargetPolicy", value = "IGNORE")
    @ProcessorOption(name = "mapstruct.unmappedSourcePolicy", value = "WARN")
    @ExpectedCompilationOutcome(
            value = CompilationResult.SUCCEEDED,
            diagnostics = {
                    @Diagnostic(type = org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class,
                            kind = Kind.WARNING,
                            line = 22,
                            message = "Unmapped source property: \"qux\"."),
                    @Diagnostic(type = org.mapstruct.ap.test.unmappedsource.SourceTargetMapperWithoutMapperConfig.class,
                            kind = Kind.WARNING,
                            line = 24,
                            message = "Unmapped source property: \"bar\".")
            }
    )
    public void shouldLeaveUnmappedSourcePropertyUnsetWithWarnPolicySetViaProcessorOption() {
    }

}
