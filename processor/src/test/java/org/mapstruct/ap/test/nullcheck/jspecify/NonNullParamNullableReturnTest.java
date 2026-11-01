/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithClasses( {
    SourceBean.class,
    TargetBean.class,
    NonNullParamNullableReturnMapper.class
} )
class NonNullParamNullableReturnTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void nullSourceSkipsNonNullParamAndNonNullTarget() {
        NonNullParamNullableReturnMapper mapper = NonNullParamNullableReturnMapper.INSTANCE;

        SourceBean nullSource = new SourceBean();
        nullSource.setNullableValue( null );

        TargetBean target = mapper.map( nullSource );

        assertThat( target.getNonNullTargetFromNullable() ).isNull();
        assertThat( target.isNonNullTargetFromNullableSet() ).isFalse();
    }

    @ProcessorTest
    void skipsNullValueAssignment() {
        NonNullParamNullableReturnMapper mapper = NonNullParamNullableReturnMapper.INSTANCE;

        SourceBean nullSource = new SourceBean();
        nullSource.setNullableValue( "toNull" );

        TargetBean target = mapper.map( nullSource );

        assertThat( target.getNonNullTargetFromNullable() ).isNull();
        assertThat( target.isNonNullTargetFromNullableSet() ).isFalse();
    }

    @ProcessorTest
    void nonNullSourcePassesThroughCustomMethod() {
        NonNullParamNullableReturnMapper mapper = NonNullParamNullableReturnMapper.INSTANCE;

        SourceBean filledSource = new SourceBean();
        filledSource.setNullableValue( "hello" );

        TargetBean target = mapper.map( filledSource );

        assertThat( target.getNonNullTargetFromNullable() ).isEqualTo( "hello" );
        assertThat( target.isNonNullTargetFromNullableSet() ).isTrue();
    }

    @ProcessorTest
    void generatedFixture() {
        generatedSource.addComparisonToFixtureFor( NonNullParamNullableReturnMapper.class );
    }
}
