/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables;

import org.immutables.value.Value;

@Value.Immutable
public interface Address {
    String getAddressLine();
}
