/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.bugs._3601.single.OnlyListMultiConditionMapper;
import org.mapstruct.ap.test.bugs._3601.single.OnlyListPropertyConditionMapper;
import org.mapstruct.ap.test.bugs._3601.single.OnlyListSourceParameterConditionMapper;
import org.mapstruct.ap.test.bugs._3601.single.OnlyStringMultiConditionMapper;
import org.mapstruct.ap.test.bugs._3601.single.OnlyStringPropertyConditionMapper;
import org.mapstruct.ap.test.bugs._3601.single.OnlyStringSourceParameterConditionMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Oliver Erhart
 */
@IssueKey("3601")
class Issue3601Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Nested
    @WithClasses({ Source.class, Target.class })
    class OnlyOneConditionPresent {

        @ProcessorTest
        @WithClasses(OnlyListPropertyConditionMapper.class)
        void testOnlyListPropertyConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyListPropertyConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(OnlyListSourceParameterConditionMapper.class)
        void testOnlyListSourceParameterConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyListSourceParameterConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(OnlyListMultiConditionMapper.class)
        void testOnlyListMultiConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyListMultiConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(OnlyStringPropertyConditionMapper.class)
        void testOnlyStringPropertyConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyStringPropertyConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(OnlyStringSourceParameterConditionMapper.class)
        void testOnlyStringSourceParameterConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyStringSourceParameterConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(OnlyStringMultiConditionMapper.class)
        void testOnlyStringMultiConditionMapper() {
            generatedSource.addComparisonToFixtureFor( OnlyStringMultiConditionMapper.class );
        }

    }


    @Nested
    @WithClasses({ Source.class, Target.class })
    class BothConditionsPresent {

        @ProcessorTest
        @WithClasses(StringPropertyConditionListPropertyConditionMapper.class)
        void testStringPropertyConditionListPropertyConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringPropertyConditionListPropertyConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringSourceParameterConditionListSourceParameterConditionMapper.class)
        void testStringSourceParameterConditionListSourceParameterConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringSourceParameterConditionListSourceParameterConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringMultiConditionListMultiConditionMapper.class)
        void testStringMultiConditionListMultiConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringMultiConditionListMultiConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringPropertyConditionListMultiConditionMapper.class)
        void testStringPropertyConditionListMultiConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringPropertyConditionListMultiConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringPropertyConditionListSourceParameterConditionMapper.class)
        void testStringPropertyConditionListSourceParameterConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringPropertyConditionListSourceParameterConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringSourceParameterConditionListPropertyConditionMapper.class)
        void testStringSourceParameterConditionListPropertyConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringSourceParameterConditionListPropertyConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringSourceParameterConditionListMultiConditionMapper.class)
        void testStringSourceParameterConditionListMultiConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringSourceParameterConditionListMultiConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringMultiConditionListPropertyConditionMapper.class)
        void testStringMultiConditionListPropertyConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringMultiConditionListPropertyConditionMapper.class );
        }

        @ProcessorTest
        @WithClasses(StringMultiConditionListSourceParameterConditionMapper.class)
        void testStringMultiConditionListSourceParameterConditionMapper() {
            generatedSource.addComparisonToFixtureFor( StringMultiConditionListSourceParameterConditionMapper.class );
        }

    }
}
