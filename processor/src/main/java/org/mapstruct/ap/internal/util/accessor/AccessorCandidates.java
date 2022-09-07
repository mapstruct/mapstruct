/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class AccessorCandidates {

    private final Types types;

    private final Map<String, List<Accessor>> accessors;
    private final Map<String, TypeMirror> preferredTypes;

    public AccessorCandidates(Types types) {
        this.types = types;
        this.accessors = new LinkedHashMap<>();
        this.preferredTypes = new LinkedHashMap<>();
    }

    public AccessorCandidates(AccessorCandidates source) {
        this.types = source.types;
        this.accessors = new LinkedHashMap<>( source.accessors );
        this.preferredTypes = new LinkedHashMap<>( source.preferredTypes );
    }

    public void put(String key, Accessor value) {
        accessors.computeIfAbsent( key, (k) -> new ArrayList<>() ).add( value );
    }

    public boolean containsKey(String key) {
        return accessors.containsKey( key );
    }

    public Set<String> keySet() {
        return accessors.keySet();
    }

    public void overrideAll(Map<String, Accessor> map) {
        map.forEach( (key, value) -> override( key, value ) );
    }

    private void override(String key, Accessor value) {
        List<Accessor> values = new ArrayList<>();
        values.add( value );

        accessors.put( key, values );
    }

    public void remove(String key) {
        accessors.remove( key );
    }

    public List<Accessor> get(String key) {
        if ( accessors.containsKey( key ) ) {
            return accessors.get( key );
        }
        return new ArrayList<>();
    }

    public Accessor getBestFit(String targetPropertyName) {
        return getBestFit( targetPropertyName, null );
    }

    public Accessor getBestFit(String targetPropertyName, TypeMirror type) {
        List<Accessor> accessors = get( targetPropertyName );
        if ( accessors.size() == 1 ) {
            return accessors.get( 0 );
        }

        if ( type == null ) {
            type = preferredTypes.get( targetPropertyName );
        }

        Predicate<Accessor> filter;
        if ( type == null ) {
            filter = accessor -> true;
        }
        else {
            TypeMirror expectedType = type;
            filter = accessor -> types.isAssignable( accessor.getAccessedType(), expectedType );
        }

        return accessors.stream().filter( filter ).findFirst().orElse( null );
    }

    public boolean isEmpty() {
        return accessors.isEmpty();
    }

    public int size() {
        return accessors.size();
    }

    public void setPreferedType(String targetPropertyName, TypeMirror preferredType) {
        this.preferredTypes.put( targetPropertyName, preferredType );
    }

}
