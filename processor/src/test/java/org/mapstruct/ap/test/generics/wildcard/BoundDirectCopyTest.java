/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author hduelme
 *
 */
public class BoundDirectCopyTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
            BoundCopyMapper.class
    );

    @ProcessorTest
    @WithClasses({
            SimpleObject.class,
            CollectionSuperTypes.class,
            CollectionExtendTypes.class,
            MapSuperType.class,
            MapExtendType.class,
            BoundCopyMapper.class
    })
    public void shouldCopyBoundedDirectly() {

    }
}
