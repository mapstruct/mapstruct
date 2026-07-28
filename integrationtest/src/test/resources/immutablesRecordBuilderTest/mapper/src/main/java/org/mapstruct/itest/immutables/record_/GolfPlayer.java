/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables.record_;

import javax.annotation.Nullable;

import org.immutables.value.Value;

/**
 * A Java record annotated with {@code @Value.Builder}. The Immutables annotation processor generates a companion
 * {@code GolfPlayerBuilder} class for it, following the {@code {RecordName}Builder} naming convention.
 * <p>
 * {@code handicap} is {@code @Nullable} (a new player may not yet have an established handicap), whereas
 * {@code name} and {@code age} are mandatory. The generated builder enforces this: attempting to build without
 * setting a mandatory field throws an exception, which would not happen if MapStruct fell back to calling the
 * record constructor directly.
 */
@Value.Builder
public record GolfPlayer(String name, @Nullable Integer handicap, int age) {
}