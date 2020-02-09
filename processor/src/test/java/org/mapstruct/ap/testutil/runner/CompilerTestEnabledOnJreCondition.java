/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.mapstruct.ap.testutil.runner.ProcessorTestInvocationContext.CURRENT_VERSION;

/**
 * Every compiler is registered with it's max supported JRE that it can run on.
 * This condition is used to check if the test for a particular compiler can be run with the current JRE.
 *
 * @author Filip Hrisafov
 */
public class CompilerTestEnabledOnJreCondition implements ExecutionCondition {

    static final ConditionEvaluationResult ENABLED_ON_CURRENT_JRE =
        ConditionEvaluationResult.enabled( "Enabled on JRE version: " + System.getProperty( "java.version" ) );

    static final ConditionEvaluationResult DISABLED_ON_CURRENT_JRE =
        ConditionEvaluationResult.disabled( "Disabled on JRE version: " + System.getProperty( "java.version" ) );

    protected final Compiler compiler;

    public CompilerTestEnabledOnJreCondition(Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // If the max JRE is greater or equal to the current version the test is enabled
        return compiler.maxJre().compareTo( CURRENT_VERSION ) >= 0 ? ENABLED_ON_CURRENT_JRE :
            DISABLED_ON_CURRENT_JRE;
    }
}
