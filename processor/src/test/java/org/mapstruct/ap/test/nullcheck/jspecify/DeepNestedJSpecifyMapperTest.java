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

/**
 * Tests JSpecify types-use annotations on three-level nested types. The generated reference name contains
 * multiple dots (nested classes are always referenced by qualified name), so the annotation must be placed
 * before the <em>last</em> simple name segment, e.g. {@code DeepNestedTargetBean.Middle.@NonNull Inner}.
 */
class DeepNestedJSpecifyMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithJSpecify
    @WithClasses({ DeepNestedSourceBean.class, DeepNestedTargetBean.class, DeepNestedJSpecifyMapper.class })
    void shouldPlaceAnnotationBeforeLastNestedSegment() {
        generatedSource.addComparisonToFixtureFor( DeepNestedJSpecifyMapper.class );

        DeepNestedSourceBean.Middle.Inner source = new DeepNestedSourceBean.Middle.Inner();
        source.setName( "name" );

        DeepNestedTargetBean.Middle.Inner target = DeepNestedJSpecifyMapper.INSTANCE.mapNonNull( source );
        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "name" );

        target = DeepNestedJSpecifyMapper.INSTANCE.mapNullable( new DeepNestedSourceBean.Middle.Inner() );
        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isNull();
    }
}
