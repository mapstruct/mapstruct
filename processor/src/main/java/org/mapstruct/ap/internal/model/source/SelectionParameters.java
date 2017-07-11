/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.SourceRHS;

/**
 * Holding parameters common to the selection process, common to IterableMapping, BeanMapping, PropertyMapping and
 * MapMapping
 *
 * @author Sjaak Derksen
 */
public class SelectionParameters {

    private final List<TypeMirror> qualifiers;
    private final List<String> qualifyingNames;
    private final TypeMirror resultType;
    private final Types typeUtils;
    private final SourceRHS sourceRHS;

    public SelectionParameters(List<TypeMirror> qualifiers, List<String> qualifyingNames, TypeMirror resultType,
        Types typeUtils) {
        this( qualifiers, qualifyingNames, resultType, typeUtils, null );
    }

    private SelectionParameters(List<TypeMirror> qualifiers, List<String> qualifyingNames, TypeMirror resultType,
        Types typeUtils, SourceRHS sourceRHS) {
        this.qualifiers = qualifiers;
        this.qualifyingNames = qualifyingNames;
        this.resultType = resultType;
        this.typeUtils = typeUtils;
        this.sourceRHS = sourceRHS;
    }

    /**
     *
     * @return qualifiers used for further select the appropriate mapping method based on class and name
     */
    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    /**
     *
     * @return qualifyingNames see qualifiers, used in combination with with @Named
     */
    public List<String> getQualifyingNames() {
        return qualifyingNames;
    }

    /**
     *
     * @return resultType used for further select the appropriate mapping method based on resultType (bean mapping)
     * targetType (Iterable- and MapMapping)
     */
    public TypeMirror getResultType() {
        return resultType;
    }

    /**
     * @return sourceRHS used for further selection of an appropriate factory method
     */
    public SourceRHS getSourceRHS() {
        return sourceRHS;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.qualifyingNames != null ? this.qualifyingNames.hashCode() : 0);
        hash = 97 * hash + (this.resultType != null ? this.resultType.toString().hashCode() : 0);
        return hash;
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
        final SelectionParameters other = (SelectionParameters) obj;

        if ( !equals( this.qualifiers, other.qualifiers ) ) {
            return false;
        }

        if ( !equals( this.qualifyingNames, other.qualifyingNames ) ) {
            return false;
        }

        if ( !equals( this.sourceRHS, other.sourceRHS ) ) {
            return false;
        }

        return equals( this.resultType, other.resultType );
    }

    private boolean equals(Object object1, Object object2) {
        if ( object1 == null ) {
            return (object2 == null);
        }
        else {
            return object1.equals( object2 );
        }
    }

    private boolean equals(List<TypeMirror> mirrors1, List<TypeMirror> mirrors2) {
        if ( mirrors1 == null ) {
            return (mirrors2 == null);
        }
        else if ( mirrors2 == null || mirrors1.size() != mirrors2.size() ) {
            return false;
        }

        for ( int i = 0; i < mirrors1.size(); i++ ) {
            if ( !equals( mirrors1.get( i ), mirrors2.get( i ) ) ) {
                return false;
            }
        }
        return true;
    }

    private boolean equals(TypeMirror mirror1, TypeMirror mirror2) {
        if ( mirror1 == null ) {
            return (mirror2 == null);
        }
        else {
            return mirror2 != null && typeUtils.isSameType( mirror1, mirror2 );
        }
    }

    public static SelectionParameters forSourceRHS(SourceRHS sourceRHS) {
        return new SelectionParameters(
            Collections.<TypeMirror>emptyList(),
            Collections.<String>emptyList(),
            null,
            null,
            sourceRHS
        );
    }
}
