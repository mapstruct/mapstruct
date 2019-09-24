/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.presencecheck;

import org.mapstruct.ap.spi.PresenceCheckerStrategy;

/**
 * @author Kirill Baurchanu
 */
public class InvertedPresenceCheckerStrategy implements PresenceCheckerStrategy {

    @Override
    public boolean isInverted() {
        return true;
    }

}
