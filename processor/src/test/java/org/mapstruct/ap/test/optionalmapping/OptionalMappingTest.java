/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import org.junit.jupiter.api.Nested;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for mapping logic from {@link org.mapstruct.ap.internal.model.OptionalMappingMethod}.
 *
 * @author Ken Wang
 */
public class OptionalMappingTest {

//    @RegisterExtension
//    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( OptionalTestMapper.class );

    @Nested
    @WithClasses({ SimpleOptionalTestMapper.class, BaseOptionalTestMapper.class, Source.class, Target.class })
    class SimpleTests extends BaseOptionalMappingTest {

        protected SimpleTests() {
            super( SimpleOptionalTestMapper.class );
        }
    }

    @Nested
    @WithClasses({ NullValueCheckAlwaysOptionalTestMapper.class, BaseOptionalTestMapper.class, Source.class, Target.class })
    class NullValueCheckAlwaysTests extends BaseOptionalMappingTest {

        protected NullValueCheckAlwaysTests() {
            super( NullValueCheckAlwaysOptionalTestMapper.class );
        }
    }

}
