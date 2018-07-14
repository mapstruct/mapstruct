/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/**
 * This class groups the selection criteria in one class
 *
 * @author Sjaak Derksen
 */
public class SelectionCriteria {

    private final List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
    private final List<String> qualifiedByNames = new ArrayList<String>();
    private final String targetPropertyName;
    private final TypeMirror qualifyingResultType;
    private final SourceRHS sourceRHS;
    private boolean preferUpdateMapping;
    private final boolean objectFactoryRequired;
    private final boolean lifecycleCallbackRequired;

    public SelectionCriteria(SelectionParameters selectionParameters, String targetPropertyName,
                             boolean preferUpdateMapping, boolean objectFactoryRequired,
                             boolean lifecycleCallbackRequired) {
        if ( selectionParameters != null ) {
            qualifiers.addAll( selectionParameters.getQualifiers() );
            qualifiedByNames.addAll( selectionParameters.getQualifyingNames() );
            qualifyingResultType = selectionParameters.getResultType();
            sourceRHS = selectionParameters.getSourceRHS();
        }
        else {
            this.qualifyingResultType = null;
            sourceRHS = null;
        }
        this.targetPropertyName = targetPropertyName;
        this.preferUpdateMapping = preferUpdateMapping;
        this.objectFactoryRequired = objectFactoryRequired;
        this.lifecycleCallbackRequired = lifecycleCallbackRequired;
    }

    /**
     * @return true if factory methods should be selected, false otherwise.
     */
    public boolean isObjectFactoryRequired() {
        return objectFactoryRequired;
    }

    /**
     * @return true if lifecycle callback methods should be selected, false otherwise.
     */
    public boolean isLifecycleCallbackRequired() {
        return lifecycleCallbackRequired;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public List<String> getQualifiedByNames() {
        return qualifiedByNames;
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public TypeMirror getQualifyingResultType() {
        return qualifyingResultType;
    }

    public boolean isPreferUpdateMapping() {
        return preferUpdateMapping;
    }

    public SourceRHS getSourceRHS() {
        return sourceRHS;
    }

    public void setPreferUpdateMapping(boolean preferUpdateMapping) {
        this.preferUpdateMapping = preferUpdateMapping;
    }

    public static SelectionCriteria forMappingMethods(SelectionParameters selectionParameters,
                                                      String targetPropertyName, boolean preferUpdateMapping) {

        return new SelectionCriteria( selectionParameters, targetPropertyName, preferUpdateMapping, false, false );
    }

    public static SelectionCriteria forFactoryMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, false, true, false );
    }

    public static SelectionCriteria forLifecycleMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, false, false, true );
    }
}
