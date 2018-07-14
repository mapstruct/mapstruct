/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.auto.value;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Address {
    public abstract String getAddressLine();

    public static Builder builder() {
        return new AutoValue_Address.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder addressLine(String addressLine);

        public abstract Address build();
    }
}
