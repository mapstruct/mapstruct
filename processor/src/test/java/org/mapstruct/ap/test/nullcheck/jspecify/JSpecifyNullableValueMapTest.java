/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
class JSpecifyNullableValueMapTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNullableValueMapMapper.class
    })
    void nullValuesAreKeptAsNullInTargetMap() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNullableValueMapMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        Map<String, NullMarkedSourceBean> sources = new LinkedHashMap<>();
        sources.put( "null-value", null );
        sources.put( "mapped-value", source );

        // null values are kept as null in the target map (set-to-null), non-null values are mapped.
        Map<String, NullMarkedTargetBean> targets = JSpecifyNullableValueMapMapper.INSTANCE.mapAll( sources );

        assertThat( targets ).hasSize( 2 );
        assertThat( targets.get( "null-value" ) ).isNull();
        assertThat( targets.get( "mapped-value" ) ).isNotNull();
        assertThat( targets.get( "mapped-value" ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
