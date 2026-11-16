/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * All write accessors of a type indexed by property name. A property can have several write accessors when its
 * setter is overloaded; one of them is the default, the others are only used when the source type cannot be mapped
 * onto the default.
 */
public class AccessorCandidates {

    private final Types types;

    private final Map<String, List<Accessor>> accessors;
    private final Map<String, Accessor> defaults;

    public AccessorCandidates(Types types) {
        this.types = types;
        this.accessors = new LinkedHashMap<>();
        this.defaults = new LinkedHashMap<>();
    }

    public AccessorCandidates(AccessorCandidates source) {
        this.types = source.types;
        this.accessors = new LinkedHashMap<>( source.accessors );
        this.defaults = new LinkedHashMap<>( source.defaults );
    }

    public void put(String key, Accessor value, boolean isDefault) {
        accessors.computeIfAbsent( key, k -> new ArrayList<>() ).add( value );
        if ( isDefault || !defaults.containsKey( key ) ) {
            defaults.put( key, value );
        }
    }

    public void overrideAll(Map<String, Accessor> map) {
        map.forEach( (key, value) -> {
            accessors.put( key, new ArrayList<>( Collections.singletonList( value ) ) );
            defaults.put( key, value );
        } );
    }

    public boolean containsKey(String key) {
        return accessors.containsKey( key );
    }

    public Set<String> keySet() {
        return accessors.keySet();
    }

    public void remove(String key) {
        accessors.remove( key );
        defaults.remove( key );
    }

    public List<Accessor> get(String key) {
        return accessors.getOrDefault( key, Collections.emptyList() );
    }

    public Accessor getDefault(String key) {
        return defaults.get( key );
    }

    public boolean isEmpty() {
        return accessors.isEmpty();
    }

    public int size() {
        return accessors.size();
    }

    public Accessor getBestFit(String targetPropertyName, TypeMirror sourceType,
                               BiPredicate<TypeMirror, TypeMirror> convertible) {
        Accessor defaultAccessor = defaults.get( targetPropertyName );
        List<Accessor> candidates = get( targetPropertyName );
        if ( candidates.size() <= 1 || sourceType == null || isMappable( sourceType, defaultAccessor, convertible ) ) {
            return defaultAccessor;
        }

        return firstAccepting( candidates, sourceType ).orElse( defaultAccessor );
    }

    private boolean isMappable(TypeMirror sourceType, Accessor accessor,
                               BiPredicate<TypeMirror, TypeMirror> convertible) {
        TypeMirror targetType = accessor.getAccessedType();
        return types.isAssignable( sourceType, targetType ) || convertible.test( sourceType, targetType );
    }

    private Optional<Accessor> firstAccepting(List<Accessor> candidates, TypeMirror sourceType) {
        Optional<Accessor> sameType = candidates.stream()
            .filter( accessor -> types.isSameType( sourceType, accessor.getAccessedType() ) )
            .findFirst();
        if ( sameType.isPresent() ) {
            return sameType;
        }
        return candidates.stream()
            .filter( accessor -> types.isAssignable( sourceType, accessor.getAccessedType() ) )
            .findFirst();
    }

}
