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
class JSpecifyContainerPropertyTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        JSpecifyContainerSourceBean.class,
        JSpecifyContainerTargetBean.class,
        JSpecifyContainerPropertyMapper.class
    })
    void listMapAndStreamContainerPropertiesAreAssignedDirectlyToNonNullSetters() {
        generatedSource.addComparisonToFixtureFor( JSpecifyContainerPropertyMapper.class );

        JSpecifyContainerSourceBean source = new JSpecifyContainerSourceBean();

        JSpecifyContainerTargetBean target = JSpecifyContainerPropertyMapper.INSTANCE.map( source );
        assertThat( target.isEntriesSet() ).isFalse();
        assertThat( target.isItemsSet() ).isFalse();
        assertThat( target.isValuesSet() ).isFalse();

    }
}
