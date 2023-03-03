/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for mapping logic from {@link org.mapstruct.ap.internal.model.OptionalMappingMethod}.
 *
 * @author Ken Wang
 */
public class OptionalMappingTest {

    @Nested
    @WithClasses({
        SimpleOptionalTestMapper.class,
        BaseOptionalTestMapper.class,
        Source.class,
        Target.class })
    class SimpleTests extends BaseOptionalMappingTest {

        @RegisterExtension
        final GeneratedSource generatedSource =
            new GeneratedSource().addComparisonToFixtureFor( SimpleOptionalTestMapper.class );

        protected SimpleTests() {
            super( SimpleOptionalTestMapper.class );
        }

        @ProcessorTest
        public void testEmptyMonitoredOptionalStillCallsSetter() {
            Source source = new Source( null, null, null, null, null, null );
            source.setMonitoredOptionalToString( Optional.empty() );

            Target target = getMapper().toTarget( source );
            assertThat( target.getMonitoredOptionalToString() ).isNull();
            assertThat( target.isMonitoredOptionalToStringWasCalled() ).isTrue();
        }

        @ProcessorTest
        public void testEmptyMonitoredSubTypeOptionalStillCallsSetter() {
            Source source = new Source( null, null, null, null, null, null );
            source.setMonitoredOptionalSubTypeToSubType( Optional.empty() );

            Target target = getMapper().toTarget( source );
            assertThat( target.getMonitoredOptionalSubTypeToSubType() ).isNull();
            assertThat( target.isMonitoredOptionalSubTypeToSubTypeWasCalled() ).isTrue();
        }

        @ProcessorTest
        public void testMappingEmptyPublicOptionalToNonOptionalWithDefaultSetsToNull() {
            Source source = new Source( null, null, null, null, null, null );
            source.publicOptionalToNonOptionalWithDefault = Optional.empty();

            Target target = getMapper().toTarget( source );
            assertThat( target.publicOptionalToNonOptionalWithDefault ).isEqualTo( null );
        }

    }

    @Nested
    @WithClasses({
        NullValueCheckAlwaysOptionalTestMapper.class,
        BaseOptionalTestMapper.class,
        Source.class,
        Target.class })
    class NullValueCheckAlwaysTests extends BaseOptionalMappingTest {

        @RegisterExtension
        final GeneratedSource generatedSource =
            new GeneratedSource().addComparisonToFixtureFor( NullValueCheckAlwaysOptionalTestMapper.class );

        protected NullValueCheckAlwaysTests() {
            super( NullValueCheckAlwaysOptionalTestMapper.class );
        }

        @ProcessorTest
        public void testEmptyMonitoredOptionalDoesNotCallSetter() {
            Source source = new Source( null, null, null, null, null, null );
            source.setMonitoredOptionalToString( Optional.empty() );

            Target target = getMapper().toTarget( source );
            assertThat( target.getMonitoredOptionalToString() ).isNull();
            assertThat( target.isMonitoredOptionalToStringWasCalled() ).isFalse();
        }

        @ProcessorTest
        public void testEmptyMonitoredSubTypeOptionalDoesNotCallSetter() {
            Source source = new Source( null, null, null, null, null, null );
            source.setMonitoredOptionalSubTypeToSubType( Optional.empty() );

            Target target = getMapper().toTarget( source );
            assertThat( target.getMonitoredOptionalSubTypeToSubType() ).isNull();
            assertThat( target.isMonitoredOptionalSubTypeToSubTypeWasCalled() ).isFalse();
        }

        @ProcessorTest
        public void testMappingEmptyPublicOptionalToNonOptionalWithDefaultDoesNotOverwrite() {
            Source source = new Source( null, null, null, null, null, null );
            source.publicOptionalToNonOptionalWithDefault = Optional.empty();

            Target target = getMapper().toTarget( source );
            assertThat( target.publicOptionalToNonOptionalWithDefault ).isEqualTo( "DEFAULT" );
        }
    }

}
