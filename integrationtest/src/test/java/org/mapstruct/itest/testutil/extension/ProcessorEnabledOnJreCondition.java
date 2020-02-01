/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.extension;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.mapstruct.itest.testutil.extension.ProcessorTestTemplateInvocationContext.CURRENT_VERSION;

/**
 * @author Filip Hrisafov
 */
public class ProcessorEnabledOnJreCondition implements ExecutionCondition {

    static final ConditionEvaluationResult ENABLED_ON_CURRENT_JRE =
        ConditionEvaluationResult.enabled( "Enabled on JRE version: " + System.getProperty( "java.version" ) );

    static final ConditionEvaluationResult DISABLED_ON_CURRENT_JRE =
        ConditionEvaluationResult.disabled( "Disabled on JRE version: " + System.getProperty( "java.version" ) );

    public ProcessorEnabledOnJreCondition(ProcessorTest.ProcessorType processorType) {
        this.processorType = processorType;
    }

    protected final ProcessorTest.ProcessorType processorType;

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        // If the max JRE is greater or equal to the current version the test is enabled
        return processorType.maxJre().compareTo( CURRENT_VERSION ) >= 0 ? ENABLED_ON_CURRENT_JRE :
            DISABLED_ON_CURRENT_JRE;
    }
}
