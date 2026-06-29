/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables;

import org.immutables.value.Value;

/**
 * Interface annotated with {@code @Value.Immutable}. Immutables generates {@link ImmutablePerson} for it;
 * here the implementation is hand-crafted to avoid needing the Immutables processor.
 */
@Value.Immutable
public interface Person {

    String getName();

    int getAge();
}
