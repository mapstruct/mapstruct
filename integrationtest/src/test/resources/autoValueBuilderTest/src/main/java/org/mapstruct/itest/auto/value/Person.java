/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.auto.value;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Person {
    public abstract String getName();
    public abstract int getAge();
    public abstract Address getAddress();

    public static Builder builder() {
        return new AutoValue_Person.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder name(String name);
        public abstract Builder age(int age);
        public abstract Builder address(Address address);

        public abstract Person build();
    }
}
