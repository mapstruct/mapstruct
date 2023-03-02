/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for mapping logic from {@link org.mapstruct.ap.internal.model.OptionalMappingMethod}.
 *
 * @author Ken Wang
 */
public class OptionalMappingTest {

    @Nested
    @WithClasses({ SimpleOptionalTestMapper.class, BaseOptionalTestMapper.class, Source.class, Target.class })
    class SimpleTests extends BaseOptionalMappingTest {

        @RegisterExtension
        final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( SimpleOptionalTestMapper.class );

        protected SimpleTests() {
            super( SimpleOptionalTestMapper.class );
        }
    }

    @Nested
    @WithClasses({ NullValueCheckAlwaysOptionalTestMapper.class, BaseOptionalTestMapper.class, Source.class, Target.class })
    class NullValueCheckAlwaysTests extends BaseOptionalMappingTest {

        @RegisterExtension
        final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( NullValueCheckAlwaysOptionalTestMapper.class );

        protected NullValueCheckAlwaysTests() {
            super( NullValueCheckAlwaysOptionalTestMapper.class );
        }
    }

}
