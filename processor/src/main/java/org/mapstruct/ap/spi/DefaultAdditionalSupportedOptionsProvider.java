/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Collections;
import java.util.Set;

/**
 * Default impl of {@link AdditionalSupportedOptionsProvider} that returns an empty set
 * for custom supported options.
 */
public class DefaultAdditionalSupportedOptionsProvider implements AdditionalSupportedOptionsProvider {

    /**
     * Returns an empty set.
     *
     * @return an empty set
     */
    @Override
    public Set<String> getAdditionalSupportedOptions() {
        return Collections.emptySet();
    }

}
