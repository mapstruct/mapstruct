/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Collections;
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
    private boolean ignoreQualifiers = false;
    private Type type;
    private final boolean allowDirect;
    private final boolean allowConversion;
    private final boolean allowMappingMethod;
    private final boolean allow2Steps;

    public SelectionCriteria(SelectionParameters selectionParameters, MappingControl mappingControl,
                             String targetPropertyName, Type type) {
        if ( selectionParameters != null ) {
            if ( type == Type.PRESENCE_CHECK ) {
                qualifiers.addAll( selectionParameters.getConditionQualifiers() );
                qualifiedByNames.addAll( selectionParameters.getConditionQualifyingNames() );
            }
            else {
                qualifiers.addAll( selectionParameters.getQualifiers() );
                qualifiedByNames.addAll( selectionParameters.getQualifyingNames() );
            }
            qualifyingResultType = selectionParameters.getResultType();
            sourceRHS = selectionParameters.getSourceRHS();
        }
        else {
            this.qualifyingResultType = null;
            sourceRHS = null;
        }
        if ( mappingControl != null ) {
            this.allowDirect = mappingControl.allowDirect();
            this.allowConversion = mappingControl.allowTypeConversion();
            this.allowMappingMethod = mappingControl.allowMappingMethod();
            this.allow2Steps = mappingControl.allowBy2Steps();
        }
        else {
            this.allowDirect = true;
            this.allowConversion = true;
            this.allowMappingMethod = true;
            this.allow2Steps = true;
        }
        this.targetPropertyName = targetPropertyName;
        this.type = type;
    }

    /**
     * @return true if factory methods should be selected, false otherwise.
     */
    public boolean isObjectFactoryRequired() {
        return type == Type.OBJECT_FACTORY;
    }

    /**
     * @return true if lifecycle callback methods should be selected, false otherwise.
     */
    public boolean isLifecycleCallbackRequired() {
        return type == Type.LIFECYCLE_CALLBACK;
    }

    /**
     * @return {@code true} if presence check methods should be selected, {@code false} otherwise
     */
    public boolean isPresenceCheckRequired() {
        return type == Type.PRESENCE_CHECK;
    }

    public void setIgnoreQualifiers(boolean ignoreQualifiers) {
        this.ignoreQualifiers = ignoreQualifiers;
    }

    public List<TypeMirror> getQualifiers() {
        return ignoreQualifiers ? Collections.emptyList() : qualifiers;
    }

    public List<String> getQualifiedByNames() {
        return ignoreQualifiers ? Collections.emptyList() : qualifiedByNames;
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public TypeMirror getQualifyingResultType() {
        return qualifyingResultType;
    }

    public boolean isPreferUpdateMapping() {
        return type == Type.PREFER_UPDATE_MAPPING;
    }

    public SourceRHS getSourceRHS() {
        return sourceRHS;
    }

    public void setPreferUpdateMapping(boolean preferUpdateMapping) {
        this.type = preferUpdateMapping ? Type.PREFER_UPDATE_MAPPING : null;
    }

    public boolean hasQualfiers() {
        return !qualifiedByNames.isEmpty() || !qualifiers.isEmpty();
    }

    public boolean isAllowDirect() {
        return allowDirect;
    }

    public boolean isAllowConversion() {
        return allowConversion;
    }

    public boolean isAllowMappingMethod() {
        return allowMappingMethod;
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
            preferUpdateMapping ? Type.PREFER_UPDATE_MAPPING : null
        );
    }

    public static SelectionCriteria forFactoryMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, null, Type.OBJECT_FACTORY );
    }

    public static SelectionCriteria forLifecycleMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, null, Type.LIFECYCLE_CALLBACK );
    }

    public static SelectionCriteria forPresenceCheckMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria(
          selectionParameters,
          null,
          selectionParameters.getTargetPropertyName(),
          Type.PRESENCE_CHECK
        );
    }

    public enum Type {
        PREFER_UPDATE_MAPPING,
        OBJECT_FACTORY,
        LIFECYCLE_CALLBACK,
        PRESENCE_CHECK,
    }
}
