/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import org.mapstruct.ap.spi.DefaultPresenceCheckerStrategy;
import org.mapstruct.ap.spi.PresenceCheckerStrategy;

/**
 * A helper class for holding the presence checker strategy.
 *
 * @author Kirill Baurchanu
 */
public class PresenceCheckerStrategyHolder {
    private static final PresenceCheckerStrategy PRESENCE_CHECKER_STRATEGY = Services.get(
        PresenceCheckerStrategy.class,
        new DefaultPresenceCheckerStrategy()
    );

    private PresenceCheckerStrategyHolder() {
    }

    public static PresenceCheckerStrategy getStrategy() {
        return PRESENCE_CHECKER_STRATEGY;
    }
}
