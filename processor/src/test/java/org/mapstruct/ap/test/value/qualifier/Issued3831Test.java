/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.qualifier;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.factories.qualified.TestQualifier;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@IssueKey("3831")
@WithClasses({ TestQualifier.class })
public class Issued3831Test {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
            DefaultValueMappingMethodMapper.class
    );

    @ProcessorTest
    @WithClasses({ DefaultValueMappingMethodMapper.class })
    public void testDefaultHandlerCalled() {
        DefaultValueMappingMethodMapper.TargetType result = DefaultValueMappingMethodMapper.INSTANCE.map(
            DefaultValueMappingMethodMapper.SourceType.B );

        assertThat( result ).isEqualTo( DefaultValueMappingMethodMapper.TargetType.Y );
        assertThat( DefaultValueMappingMethodMapper.INVOKE_LIST ).contains( "defaultHandlerWithReturn" );
        DefaultValueMappingMethodMapper.INVOKE_LIST.clear();
    }

    @ProcessorTest
    @WithClasses({ DefaultValueMappingMethodMapper.class })
    public void testDefaultHandlerCalledNoReturn() {
        DefaultValueMappingMethodMapper.TargetType result = DefaultValueMappingMethodMapper.INSTANCE.mapNoReturn(
            DefaultValueMappingMethodMapper.SourceType.C );

        assertThat( result ).isEqualTo( DefaultValueMappingMethodMapper.TargetType.Z );
        assertThat( DefaultValueMappingMethodMapper.INVOKE_LIST ).contains( "defaultHandlerNoReturn" );
        DefaultValueMappingMethodMapper.INVOKE_LIST.clear();
    }
}
