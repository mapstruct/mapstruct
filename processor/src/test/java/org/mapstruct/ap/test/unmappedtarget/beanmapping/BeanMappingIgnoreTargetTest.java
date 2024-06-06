/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.unmappedtarget.beanmapping;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import static org.assertj.core.api.Assertions.assertThat;
@IssueKey("3539")
public class BeanMappingIgnoreTargetTest {

    @ProcessorTest
    @WithClasses({Source.class, Target.class, BeanMappingIgnoreTargetMapper.class})
    public void shouldIgnoreTargetProperty() {
        Source source = new Source();
        source.setAge( 18 );
        source.setName( "TOM" );
        source.setNested( new Source.NestedSource() );
        Target target = BeanMappingIgnoreTargetMapper.INSTANCE.convert( source );
        assertThat( target.getName() ).isEqualTo( source.getName() );
        assertThat( target.getAge() ).isEqualTo( source.getAge() );
    }

    @ProcessorTest
    @WithClasses({Source.class, Target.class, ErroneousBeanMappingIgnoreTargetMapper.class})
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = ErroneousBeanMappingIgnoreTargetMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 19,
                            message = "Unknown property \"unknownProperty\" in result type Target. " +
                                    "Did you mean \"name\"?")
            }
    )
    public void shouldRaiseErrorDueToUnknownProperty() {
    }

    @ProcessorTest
    @WithClasses({Source.class, Target.class, ErroneousBeanMappingIgnoreTargetConflictMapper.class})
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = ErroneousBeanMappingIgnoreTargetConflictMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 19,
                            message = "The target property name cannot be mapped more than once in @BeanMapping and @Mapping.")
            }
    )
    public void shouldRaiseErrorDueToConflictProperty() {

    }

    @ProcessorTest
    @WithClasses({Source.class, Target.class, ErroneousBeanMappingIgnoreTargetThisMapper.class})
    @ExpectedCompilationOutcome(
            value = CompilationResult.FAILED,
            diagnostics = {
                    @Diagnostic(type = ErroneousBeanMappingIgnoreTargetThisMapper.class,
                            kind = javax.tools.Diagnostic.Kind.ERROR,
                            line = 19,
                            message = "Using @BeanMapping( ignoreTargets = {\".\"} ) " +
                                    "with @Mapping( target = \".\", ... ) is not allowed. " +
                                    "You'll need to explicitly ignore the target properties " +
                                    "that should be ignored instead.")
            }
    )
    public void shouldRaiseErrorDueToThisProperty() {

    }

}
