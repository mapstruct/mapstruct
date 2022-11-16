/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Collections;
import java.util.Set;

/**
 * Default impl of {@link CustomSupportedOptionsProvider} that returns an empty set
 * for custom supported options.
 */
public class DefaultCustomSupportedOptionsProvider implements CustomSupportedOptionsProvider {

    /**
     * Returns an empty set.
     *
     * @return an empty set
     */
    @Override
    public Set<String> getCustomSupportedOptions() {
        return Collections.emptySet();
    }

}
