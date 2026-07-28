/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.immutables.record_;

import org.immutables.value.Value;

/**
 * A Java record annotated with {@code @Value.Builder}. In a real project the Immutables annotation processor
 * generates {@link GolfPlayerBuilder} from this declaration; here the builder is hand-crafted so that the
 * MapStruct processor tests run without executing the Immutables processor.
 * <p>
 * {@code handicap} is nullable ({@code Integer}, not {@code int}), whereas {@code name} and {@code age} are
 * mandatory. The generated builder enforces this at {@code build()} time.
 */
@Value.Builder
public record GolfPlayer(String name, Integer handicap, int age) {
}

