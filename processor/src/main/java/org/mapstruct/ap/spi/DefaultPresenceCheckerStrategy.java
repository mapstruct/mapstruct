/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * A default implementation of the {@link PresenceCheckerStrategy} service provider interface.
 *
 * @author Kirill Baurchanu
 */
public class DefaultPresenceCheckerStrategy implements PresenceCheckerStrategy {

    @Override
    public boolean isInverted() {
        return false;
    }

}
