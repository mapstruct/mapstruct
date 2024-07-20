/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.lang.model.type.TypeMirror;
import org.mapstruct.ap.internal.util.TypeUtils;

import org.mapstruct.ap.internal.model.common.SourceRHS;

/**
 * Holding parameters common to the selection process, common to IterableMapping, BeanMapping, PropertyMapping and
 * MapMapping
 *
 * @author Sjaak Derksen
 */
public class SelectionParameters {

    private static final SelectionParameters EMPTY = new SelectionParameters(
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        Collections.emptyList(),
        null,
        null,
        null
    );

    private final List<TypeMirror> qualifiers;
    private final List<String> qualifyingNames;
    private final List<TypeMirror> conditionQualifiers;
    private final List<String> conditionQualifyingNames;
    private final TypeMirror resultType;
    private final TypeUtils typeUtils;
    private final SourceRHS sourceRHS;

    /**
     * Returns new selection parameters
     *
     * ResultType is not inherited.
     *
     * @param selectionParameters the selection parameters that need to be copied
     *
     * @return the selection parameters based on the given ones
     */
    public static SelectionParameters forInheritance(SelectionParameters selectionParameters) {
        return withoutResultType( selectionParameters );
    }

    public static SelectionParameters withoutResultType(SelectionParameters selectionParameters) {
        return new SelectionParameters(
            selectionParameters.qualifiers,
            selectionParameters.qualifyingNames,
            selectionParameters.conditionQualifiers,
            selectionParameters.conditionQualifyingNames,
            null,
            selectionParameters.typeUtils
        );
    }

    public SelectionParameters(List<TypeMirror> qualifiers, List<String> qualifyingNames, TypeMirror resultType,
        TypeUtils typeUtils) {
        this(
            qualifiers,
            qualifyingNames,
            Collections.emptyList(),
            Collections.emptyList(),
            resultType,
            typeUtils,
            null
        );
    }

    public SelectionParameters(List<TypeMirror> qualifiers, List<String> qualifyingNames,
                               List<TypeMirror> conditionQualifiers, List<String> conditionQualifyingNames,
                               TypeMirror resultType,
                               TypeUtils typeUtils) {
        this( qualifiers, qualifyingNames, conditionQualifiers, conditionQualifyingNames, resultType, typeUtils, null );
    }

    private SelectionParameters(List<TypeMirror> qualifiers, List<String> qualifyingNames,
                                List<TypeMirror> conditionQualifiers, List<String> conditionQualifyingNames,
                                TypeMirror resultType,
                                TypeUtils typeUtils, SourceRHS sourceRHS) {
        this.qualifiers = qualifiers;
        this.qualifyingNames = qualifyingNames;
        this.conditionQualifiers = conditionQualifiers;
        this.conditionQualifyingNames = conditionQualifyingNames;
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
     * @return qualifiers used for further select the appropriate presence check method based on class and name
     */
    public List<TypeMirror> getConditionQualifiers() {
        return conditionQualifiers;
    }

    /**
     * @return qualifyingNames, used in combination with with @Named
     * @see #getConditionQualifiers()
     */
    public List<String> getConditionQualifyingNames() {
        return conditionQualifyingNames;
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

        if ( !Objects.equals( this.qualifyingNames, other.qualifyingNames ) ) {
            return false;
        }

        if ( !Objects.equals( this.conditionQualifiers, other.conditionQualifiers ) ) {
            return false;
        }

        if ( !Objects.equals( this.conditionQualifyingNames, other.conditionQualifyingNames ) ) {
            return false;
        }

        if ( !Objects.equals( this.sourceRHS, other.sourceRHS ) ) {
            return false;
        }

        return equals( this.resultType, other.resultType );
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

    public SelectionParameters withSourceRHS(SourceRHS sourceRHS) {
        return new SelectionParameters(
            this.qualifiers,
            this.qualifyingNames,
            this.conditionQualifiers,
            this.conditionQualifyingNames,
            null,
            this.typeUtils,
            sourceRHS
        );
    }

    public static SelectionParameters forSourceRHS(SourceRHS sourceRHS) {
        return new SelectionParameters(
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            Collections.emptyList(),
            null,
            null,
            sourceRHS
        );
    }

    public static SelectionParameters empty() {
        return EMPTY;
    }

}
