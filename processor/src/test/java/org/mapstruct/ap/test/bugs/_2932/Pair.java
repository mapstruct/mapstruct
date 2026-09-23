/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2932;

/**
 * A generic type with a builder that is created through its public constructor.
 */
public class Pair<K, V> {

    private final K key;
    private final V value;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public static class PairBuilder<K, V> {

        private K key;
        private V value;

        public PairBuilder() {
        }

        public PairBuilder<K, V> key(K key) {
            this.key = key;
            return this;
        }

        public PairBuilder<K, V> value(V value) {
            this.value = value;
            return this;
        }

        public Pair<K, V> build() {
            return new Pair<>( key, value );
        }
    }
}
