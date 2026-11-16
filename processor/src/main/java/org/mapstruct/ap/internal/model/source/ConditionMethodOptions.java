/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collection;
import java.util.Collections;

import org.mapstruct.ap.internal.gem.ConditionStrategyGem;

/**
 * Encapsulates all options specific for a condition check method.
 *
 * @author Filip Hrisafov
 */
public class ConditionMethodOptions {

    private static final ConditionMethodOptions EMPTY = new ConditionMethodOptions( Collections.emptyList() );

    private final Collection<ConditionOptions> conditionOptions;

    public ConditionMethodOptions(Collection<ConditionOptions> conditionOptions) {
        this.conditionOptions = conditionOptions;
    }

    public boolean isStrategyApplicable(ConditionStrategyGem strategy) {
        for ( ConditionOptions conditionOption : conditionOptions ) {
            if ( conditionOption.getConditionStrategies().contains( strategy ) ) {
                return true;
            }
        }

        return false;
    }

    public boolean isAnyStrategyApplicable() {
        return !conditionOptions.isEmpty();
    }

    public static ConditionMethodOptions empty() {
        return EMPTY;
    }
}
