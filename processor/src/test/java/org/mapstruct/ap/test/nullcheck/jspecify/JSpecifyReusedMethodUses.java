/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Named;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.ChainIntermediate;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.ChainTargetValue;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.ChainValue;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.DirectTargetValue;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.DirectValue;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.GuardedTargetValue;
import org.mapstruct.ap.test.nullcheck.jspecify.JSpecifyReusedMethodMapper.GuardedValue;

@NullMarked
public class JSpecifyReusedMethodUses {

    @Named("nonNull")
    public GuardedTargetValue mapNonNull(
        GuardedValue source
    ) {
        return new GuardedTargetValue( source.value() );
    }

    @Named("nullableInput")
    public @NonNull DirectTargetValue mapNullableInput(
        @Nullable DirectValue source
    ) {
        return new DirectTargetValue( source == null ? "fallback" : source.value() );
    }

    @Named("unmarked")
    @NullUnmarked
    public JSpecifyReusedMethodMapper.UnmarkedTargetValue mapUnmarked(
        JSpecifyReusedMethodMapper.UnmarkedValue source
    ) {
        return source == null ? null : new JSpecifyReusedMethodMapper.UnmarkedTargetValue( source.value() );
    }

    public ChainIntermediate mapChainStart(
        ChainValue source
    ) {
        return new ChainIntermediate( source.value() );
    }

    public @NonNull ChainTargetValue mapChainEnd(
        @Nullable ChainIntermediate source
    ) {
        return new ChainTargetValue( source == null ? "fallback" : source.value() );
    }

    @Named("list")
    public @NonNull List<GuardedTargetValue> mapList(
        @Nullable List<GuardedValue> source
    ) {
        return source == null
            ? Collections.emptyList()
            : source.stream()
                .map( value -> new GuardedTargetValue( value.value() ) )
                .toList();
    }

    @Named("map")
    public @NonNull Map<String, GuardedTargetValue> mapMap(
        @Nullable Map<String, GuardedValue> source
    ) {
        return source == null
            ? Collections.emptyMap()
            : source.entrySet().stream().collect(
                java.util.stream.Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> new GuardedTargetValue( entry.getValue().value() )
                )
            );
    }
}
