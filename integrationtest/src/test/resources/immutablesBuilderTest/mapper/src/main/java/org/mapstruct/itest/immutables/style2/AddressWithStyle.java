/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.style2;

import org.immutables.value.Value;

@Value.Immutable
@Value.Style(
    visibility = Value.Style.ImplementationVisibility.PACKAGE,
    overshadowImplementation = true,
    depluralize = true,
    jdkOnly = true)
public abstract class AddressWithStyle {

    public abstract String getAddressLine();

    public static class Builder extends ImmutableAddressWithStyle.Builder {

    }

    public static Builder builder() {
        return new Builder();
    }
}
