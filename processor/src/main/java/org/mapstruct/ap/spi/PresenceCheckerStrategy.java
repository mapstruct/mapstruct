/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * A service provider interface for specifying the strategy of presence checkers.
 *
 * @author Kirill Baurchanu
 */
public interface PresenceCheckerStrategy {

    /**
     * @return {@code false} if direct presence checks (e.g. {@code public boolean hasName()}) should be used,
     *         {@code true} if inverted presence checks (e.g. {@code public boolean hasNoName()}) should be used.
     */
    boolean isInverted();

}
