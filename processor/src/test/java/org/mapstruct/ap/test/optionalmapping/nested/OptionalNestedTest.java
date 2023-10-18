package org.mapstruct.ap.test.optionalmapping.nested;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalNestedMapper.class,
    Source.class,
    Target.class })
public class OptionalNestedTest {

    @ProcessorTest
    public void optionalToNonOptional_empty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.empty() );
        
        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptional_null() {
        Source source = new Source();
        source.setOptionalToNonOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptional_empty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptional_null() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptional_nonNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo("some value");
    }

    @ProcessorTest
    public void optionalToOptional_empty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptional_null() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedOptionalToOptional_empty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedOptionalToOptional_null() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedOptionalToOptional_nonNull() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains("some value");
    }

    @ProcessorTest
    public void nonOptionalToNonOptional_empty() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToNonOptional_null() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedNonOptionalToNonOptional_null() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedNonOptionalToNonOptional_nonNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isEqualTo("some value");
    }

    @ProcessorTest
    public void nonOptionalToOptional_empty() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nonOptionalToOptional_null() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedNonOptionalToOptional_null() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedNonOptionalToOptional_nonNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains("some value");
    }
    
}
