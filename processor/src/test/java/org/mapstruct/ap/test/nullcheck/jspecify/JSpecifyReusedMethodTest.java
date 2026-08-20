/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Collections;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.Compiler;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4106")
@WithJSpecify
@WithClasses({ JSpecifyReusedMethodMapper.class, JSpecifyReusedMethodUses.class })
class JSpecifyReusedMethodTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest(Compiler.JDK)
    void honorsReusedMethodInputAndResultContracts() {
        generatedSource.addComparisonToFixtureFor( JSpecifyReusedMethodMapper.class );

        JSpecifyReusedMethodMapper.Source source = new JSpecifyReusedMethodMapper.Source();
        JSpecifyReusedMethodMapper.Target target = JSpecifyReusedMethodMapper.INSTANCE.map( source );

        assertThat( target.isGuardedSet() ).isFalse();
        assertThat( target.isDirectSet() ).isTrue();
        assertThat( target.getDirect().value() ).isEqualTo( "fallback" );
        assertThat( target.isUnmarkedSet() ).isTrue();
        assertThat( target.getUnmarked() ).isNull();
        assertThat( target.isChainSet() ).isFalse();
        assertThat( target.isValuesSet() ).isTrue();
        assertThat( target.getValues() ).isEmpty();
        assertThat( target.isEntriesSet() ).isTrue();
        assertThat( target.getEntries() ).isEmpty();

        source.setGuarded( new JSpecifyReusedMethodMapper.GuardedValue( "guarded" ) );
        source.setDirect( new JSpecifyReusedMethodMapper.DirectValue( "direct" ) );
        source.setChain( new JSpecifyReusedMethodMapper.ChainValue( "chain" ) );
        source.setValues( Collections.singletonList( new JSpecifyReusedMethodMapper.GuardedValue( "value" ) ) );
        target = JSpecifyReusedMethodMapper.INSTANCE.map( source );

        assertThat( target.isGuardedSet() ).isTrue();
        assertThat( target.isChainSet() ).isTrue();
        assertThat( target.getValues() ).extracting( "value" ).containsExactly( "value" );
    }
}
