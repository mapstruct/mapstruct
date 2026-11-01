/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.subclass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.MAP;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithJSpecify
public class JspecifyReuseSubclassMappingMethodTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({JspecifyReuseMapper.class, AbstractSource.class, Source1.class, SourceHolder.class,
    AbstractTarget.class, Target1.class, TargetHolder.class})
    public void shouldNotAddNullCheckBeforeReusingSubclassMappingMethod() {
        generatedSource.addComparisonToFixtureFor( JspecifyReuseMapper.class );
        SourceHolder sourceHolder = new SourceHolder();
        sourceHolder.setH( new Source1() );
        TargetHolder targetHolder = JspecifyReuseMapper.INSTANCE.map( sourceHolder );
        assertThat( targetHolder ).isNotNull();
        assertThat( targetHolder.getH() ).isNotNull();
    }
}
