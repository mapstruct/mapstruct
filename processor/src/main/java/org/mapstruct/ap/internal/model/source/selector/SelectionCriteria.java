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
import org.mapstruct.ap.internal.model.source.MappingControl;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/**
 * This class groups the selection criteria in one class
 *
 * @author Sjaak Derksen
 */
public class SelectionCriteria {

    private final List<TypeMirror> qualifiers = new ArrayList<>();
    private final List<String> qualifiedByNames = new ArrayList<>();
    private final String targetPropertyName;
    private final TypeMirror qualifyingResultType;
    private final SourceRHS sourceRHS;
    private boolean preferUpdateMapping;
    private final boolean objectFactoryRequired;
    private final boolean lifecycleCallbackRequired;
    private final boolean allowDirect;
    private final boolean allowTypeConversion;
    private final boolean allowByMappingMethod;
    private final boolean allow2Steps;

    public SelectionCriteria(SelectionParameters selectionParameters, MappingControl mappingControl,
                             String targetPropertyName, boolean preferUpdateMapping, boolean objectFactoryRequired,
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
        if ( mappingControl != null ) {
            this.allowDirect = mappingControl.allowDirect();
            this.allowTypeConversion = mappingControl.allowTypeConversion();
            this.allowByMappingMethod = mappingControl.allowByMappingMethod();
            this.allow2Steps = mappingControl.allowBy2Steps();
        }
        else {
            this.allowDirect = true;
            this.allowTypeConversion = true;
            this.allowByMappingMethod = true;
            this.allow2Steps = true;
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

    public boolean hasQualfiers() {
        return !qualifiedByNames.isEmpty() || !qualifiers.isEmpty();
    }

    public boolean isAllowDirect() {
        return allowDirect;
    }

    public boolean isAllowTypeConversion() {
        return allowTypeConversion;
    }

    public boolean isAllowByMappingMethod() {
        return allowByMappingMethod;
    }

    public boolean isAllow2Steps() {
        return allow2Steps;
    }

    public static SelectionCriteria forMappingMethods(SelectionParameters selectionParameters,
                                                      MappingControl mappingControl,
                                                      String targetPropertyName, boolean preferUpdateMapping) {

        return new SelectionCriteria(
            selectionParameters,
            mappingControl,
            targetPropertyName,
            preferUpdateMapping,
            false,
            false
        );
    }

    public static SelectionCriteria forFactoryMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, null, false, true, false );
    }

    public static SelectionCriteria forLifecycleMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, null, false, false, true );
    }
}
