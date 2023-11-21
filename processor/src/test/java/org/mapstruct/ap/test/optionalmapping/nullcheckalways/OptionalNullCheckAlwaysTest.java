package org.mapstruct.ap.test.optionalmapping.nullcheckalways;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalNullCheckAlwaysMapper.class,
    Source.class,
    Target.class })
public class OptionalNullCheckAlwaysTest {

    @ProcessorTest
    public void optionalToOptional_null() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isOptionalToOptionalCalled() ).isFalse();
    }

    @ProcessorTest
    public void optionalToNonOptional_null() {
        Source source = new Source();
        source.setOptionalToNonOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isOptionalToNonOptionalCalled() ).isFalse();
    }

    @ProcessorTest
    public void nonOptionalToOptional_null() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isNonOptionalToOptionalCalled() ).isFalse();
    }
    
}
