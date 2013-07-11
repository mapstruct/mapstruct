/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents the type of a bean property, parameter etc.
 *
 * @author Gunnar Morling
 */
public class Type extends AbstractModelElement implements Comparable<Type> {

    private static final Set<String> PRIMITIVE_TYPE_NAMES = new HashSet<String>(
        Arrays.asList( "boolean", "char", "byte", "short", "int", "long", "float", "double" )
    );

    private static final ConcurrentMap<String, Type> DEFAULT_ITERABLE_IMPLEMENTATION_TYPES =
        new ConcurrentHashMap<String, Type>();
    private static final ConcurrentMap<String, Type> DEFAULT_COLLECTION_IMPLEMENTATION_TYPES =
        new ConcurrentHashMap<String, Type>();

    static {
        DEFAULT_COLLECTION_IMPLEMENTATION_TYPES.put( List.class.getName(), forClass( ArrayList.class ) );
        DEFAULT_COLLECTION_IMPLEMENTATION_TYPES.put( Set.class.getName(), forClass( HashSet.class ) );
        DEFAULT_COLLECTION_IMPLEMENTATION_TYPES.put( Collection.class.getName(), forClass( ArrayList.class ) );

        DEFAULT_ITERABLE_IMPLEMENTATION_TYPES.put( Iterable.class.getName(), forClass( ArrayList.class ) );
        DEFAULT_ITERABLE_IMPLEMENTATION_TYPES.putAll( DEFAULT_COLLECTION_IMPLEMENTATION_TYPES );
    }

    private final String packageName;
    private final String name;
    private final List<Type> typeParameters;
    private final boolean isEnumType;
    private final boolean isCollectionType;
    private final boolean isIterableType;
    private final Type collectionImplementationType;
    private final Type iterableImplementationType;

    public static Type forClass(Class<?> clazz) {
        Package pakkage = clazz.getPackage();

        if ( pakkage != null ) {
            return new Type(
                pakkage.getName(),
                clazz.getSimpleName(),
                clazz.isEnum(),
                Collection.class.isAssignableFrom( clazz ),
                Iterable.class.isAssignableFrom( clazz ),
                Collections.<Type>emptyList()
            );
        }
        else {
            return new Type( clazz.getSimpleName() );
        }
    }

    public Type(String name) {
        this( null, name, false, false, false, Collections.<Type>emptyList() );
    }

    public Type(String packageName, String name) {
        this( packageName, name, false, false, false, Collections.<Type>emptyList() );
    }

    public Type(String packageName, String name, boolean isEnumType, boolean isCollectionType,
                boolean isIterableType, List<Type> typeParameters) {
        this.packageName = packageName;
        this.name = name;
        this.isEnumType = isEnumType;
        this.isCollectionType = isCollectionType;
        this.isIterableType = isIterableType;
        this.typeParameters = typeParameters;

        if ( isCollectionType ) {
            collectionImplementationType = DEFAULT_COLLECTION_IMPLEMENTATION_TYPES.get( packageName + "." + name );
        }
        else {
            collectionImplementationType = null;
        }

        if ( isIterableType ) {
            iterableImplementationType = DEFAULT_ITERABLE_IMPLEMENTATION_TYPES.get( packageName + "." + name );
        }
        else {
            iterableImplementationType = null;
        }
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public List<Type> getTypeParameters() {
        return typeParameters;
    }

    public boolean isPrimitive() {
        return packageName == null && PRIMITIVE_TYPE_NAMES.contains( name );
    }

    public boolean isEnumType() {
        return isEnumType;
    }

    public Type getCollectionImplementationType() {
        return collectionImplementationType;
    }

    public Type getIterableImplementationType() {
        return iterableImplementationType;
    }

    public boolean isCollectionType() {
        return isCollectionType;
    }

    public boolean isIterableType() {
        return isIterableType;
    }

    public String getFullyQualifiedName() {
        return packageName == null ? name : packageName + "." + name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        result = prime * result + ( ( packageName == null ) ? 0 : packageName.hashCode() );
        result = prime * result + ( ( typeParameters == null ) ? 0 : typeParameters.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Type other = (Type) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        }
        else if ( !name.equals( other.name ) ) {
            return false;
        }
        if ( packageName == null ) {
            if ( other.packageName != null ) {
                return false;
            }
        }
        else if ( !packageName.equals( other.packageName ) ) {
            return false;
        }
        if ( typeParameters == null ) {
            if ( other.typeParameters != null ) {
                return false;
            }
        }
        else if ( !typeParameters.equals( other.typeParameters ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Type o) {
        return getFullyQualifiedName().compareTo( o.getFullyQualifiedName() );
    }
}
