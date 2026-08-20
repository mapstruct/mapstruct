/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.Map;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = JSpecifyReusedMethodUses.class)
@NullMarked
public interface JSpecifyReusedMethodMapper {

    JSpecifyReusedMethodMapper INSTANCE = Mappers.getMapper( JSpecifyReusedMethodMapper.class );

    @Mapping(target = "guarded", source = "guarded", qualifiedByName = "nonNull")
    @Mapping(target = "direct", source = "direct", qualifiedByName = "nullableInput")
    @Mapping(target = "unmarked", source = "unmarked", qualifiedByName = "unmarked")
    @Mapping(target = "values", source = "values", qualifiedByName = "list")
    @Mapping(target = "entries", source = "entries", qualifiedByName = "map")
    Target map(Source source);

    @NullMarked
    class Source {
        private @Nullable GuardedValue guarded;
        private @Nullable DirectValue direct;
        private @Nullable UnmarkedValue unmarked;
        private @Nullable ChainValue chain;
        private @Nullable List<GuardedValue> values;
        private @Nullable Map<String, GuardedValue> entries;

        public @Nullable GuardedValue getGuarded() {
            return guarded;
        }

        public void setGuarded(@Nullable GuardedValue guarded) {
            this.guarded = guarded;
        }

        public @Nullable DirectValue getDirect() {
            return direct;
        }

        public void setDirect(@Nullable DirectValue direct) {
            this.direct = direct;
        }

        public @Nullable UnmarkedValue getUnmarked() {
            return unmarked;
        }

        public void setUnmarked(@Nullable UnmarkedValue unmarked) {
            this.unmarked = unmarked;
        }

        public @Nullable ChainValue getChain() {
            return chain;
        }

        public void setChain(@Nullable ChainValue chain) {
            this.chain = chain;
        }

        public @Nullable List<GuardedValue> getValues() {
            return values;
        }

        public void setValues(@Nullable List<GuardedValue> values) {
            this.values = values;
        }

        public @Nullable Map<String, GuardedValue> getEntries() {
            return entries;
        }

        public void setEntries(@Nullable Map<String, GuardedValue> entries) {
            this.entries = entries;
        }
    }

    @NullMarked
    class Target {
        private GuardedTargetValue guarded;
        private DirectTargetValue direct;
        private UnmarkedTargetValue unmarked;
        private ChainTargetValue chain;
        private List<GuardedTargetValue> values;
        private Map<String, GuardedTargetValue> entries;
        private boolean guardedSet;
        private boolean directSet;
        private boolean unmarkedSet;
        private boolean chainSet;
        private boolean valuesSet;
        private boolean entriesSet;

        public GuardedTargetValue getGuarded() {
            return guarded;
        }

        public void setGuarded(@Nullable GuardedTargetValue guarded) {
            guardedSet = true;
            this.guarded = guarded;
        }

        public DirectTargetValue getDirect() {
            return direct;
        }

        public void setDirect(DirectTargetValue direct) {
            directSet = true;
            this.direct = direct;
        }

        public UnmarkedTargetValue getUnmarked() {
            return unmarked;
        }

        public void setUnmarked(@Nullable UnmarkedTargetValue unmarked) {
            unmarkedSet = true;
            this.unmarked = unmarked;
        }

        public ChainTargetValue getChain() {
            return chain;
        }

        public void setChain(@Nullable ChainTargetValue chain) {
            chainSet = true;
            this.chain = chain;
        }

        public List<GuardedTargetValue> getValues() {
            return values;
        }

        public void setValues(List<GuardedTargetValue> values) {
            valuesSet = true;
            this.values = values;
        }

        public Map<String, GuardedTargetValue> getEntries() {
            return entries;
        }

        public void setEntries(Map<String, GuardedTargetValue> entries) {
            entriesSet = true;
            this.entries = entries;
        }

        public boolean isGuardedSet() {
            return guardedSet;
        }

        public boolean isDirectSet() {
            return directSet;
        }

        public boolean isUnmarkedSet() {
            return unmarkedSet;
        }

        public boolean isChainSet() {
            return chainSet;
        }

        public boolean isValuesSet() {
            return valuesSet;
        }

        public boolean isEntriesSet() {
            return entriesSet;
        }
    }

    record GuardedValue(String value) {
    }

    record DirectValue(String value) {
    }

    record UnmarkedValue(String value) {
    }

    record ChainValue(String value) {
    }

    record ChainIntermediate(String value) {
    }

    record GuardedTargetValue(String value) {
    }

    record DirectTargetValue(String value) {
    }

    record UnmarkedTargetValue(String value) {
    }

    record ChainTargetValue(String value) {
    }
}
