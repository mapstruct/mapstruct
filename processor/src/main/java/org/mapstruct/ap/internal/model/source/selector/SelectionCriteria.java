/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

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

    private final QualifyingInfo qualifyingInfo;
    private final String targetPropertyName;
    private final SourceRHS sourceRHS;
    private boolean ignoreQualifiers = false;
    private Type type;
    private final MappingControl mappingControl;

    public SelectionCriteria(SelectionParameters selectionParameters, MappingControl mappingControl,
                             String targetPropertyName, Type type) {
        this(
            QualifyingInfo.fromSelectionParameters( selectionParameters ),
            selectionParameters != null ? selectionParameters.getSourceRHS() : null,
            mappingControl,
            targetPropertyName,
            type
        );
    }

    private SelectionCriteria(QualifyingInfo qualifyingInfo, SourceRHS sourceRHS, MappingControl mappingControl,
                              String targetPropertyName, Type type) {
        this.qualifyingInfo = qualifyingInfo;
        this.targetPropertyName = targetPropertyName;
        this.sourceRHS = sourceRHS;
        this.type = type;
        this.mappingControl = mappingControl;
    }

    /**
     *
     * @return {@code true} if only mapping methods should be selected
     */
    public boolean isForMapping() {
        return type == null || type == Type.PREFER_UPDATE_MAPPING;
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

    /**
     * @return {@code true} if source parameter check methods should be selected, {@code false} otherwise
     */
    public boolean isSourceParameterCheckRequired() {
        return type == Type.SOURCE_PARAMETER_CHECK;
    }

    public void setIgnoreQualifiers(boolean ignoreQualifiers) {
        this.ignoreQualifiers = ignoreQualifiers;
    }

    public List<TypeMirror> getQualifiers() {
        return ignoreQualifiers ? Collections.emptyList() : qualifyingInfo.qualifiers();
    }

    public List<String> getQualifiedByNames() {
        return ignoreQualifiers ? Collections.emptyList() : qualifyingInfo.qualifiedByNames();
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public TypeMirror getQualifyingResultType() {
        return qualifyingInfo.qualifyingResultType();
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
        return !qualifyingInfo.qualifiedByNames().isEmpty() || !qualifyingInfo.qualifiers().isEmpty();
    }

    public boolean isAllowDirect() {
        return mappingControl == null || mappingControl.allowDirect();
    }

    public boolean isAllowConversion() {
        return mappingControl == null || mappingControl.allowTypeConversion();
    }

    public boolean isAllowMappingMethod() {
        return mappingControl == null || mappingControl.allowMappingMethod();
    }

    public boolean isAllow2Steps() {
        return mappingControl == null || mappingControl.allowBy2Steps();
    }

    public boolean isSelfAllowed() {
        return type != Type.SELF_NOT_ALLOWED;
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
        SourceRHS sourceRHS = selectionParameters.getSourceRHS();
        Type type;
        QualifyingInfo qualifyingInfo = new QualifyingInfo(
            selectionParameters.getConditionQualifiers(),
            selectionParameters.getConditionQualifyingNames(),
            selectionParameters.getResultType()
        );
        if ( sourceRHS != null && sourceRHS.isSourceReferenceParameter() ) {
            // If the source reference is for a source parameter,
            // then the presence check should be for the source parameter
            type = Type.SOURCE_PARAMETER_CHECK;
        }
        else {
            type = Type.PRESENCE_CHECK;
        }
        return new SelectionCriteria( qualifyingInfo, sourceRHS, null, null, type );
    }

    public static SelectionCriteria forSourceParameterCheckMethods(SelectionParameters selectionParameters) {
        return new SelectionCriteria( selectionParameters, null, null, Type.SOURCE_PARAMETER_CHECK );
    }

    public static SelectionCriteria forSubclassMappingMethods(SelectionParameters selectionParameters,
        MappingControl mappingControl) {
        return new SelectionCriteria( selectionParameters, mappingControl, null, Type.SELF_NOT_ALLOWED );
    }

    private static class QualifyingInfo {

        private static final QualifyingInfo EMPTY = new QualifyingInfo(
            Collections.emptyList(),
            Collections.emptyList(),
            null
        );

        private final List<TypeMirror> qualifiers;
        private final List<String> qualifiedByNames;
        private final TypeMirror qualifyingResultType;

        private QualifyingInfo(List<TypeMirror> qualifiers, List<String> qualifiedByNames,
                               TypeMirror qualifyingResultType) {
            this.qualifiers = qualifiers;
            this.qualifiedByNames = qualifiedByNames;
            this.qualifyingResultType = qualifyingResultType;
        }

        public List<TypeMirror> qualifiers() {
            return qualifiers;
        }

        public List<String> qualifiedByNames() {
            return qualifiedByNames;
        }

        public TypeMirror qualifyingResultType() {
            return qualifyingResultType;
        }

        private static QualifyingInfo fromSelectionParameters(SelectionParameters selectionParameters) {
            if ( selectionParameters == null ) {
                return EMPTY;
            }
            return new QualifyingInfo(
                selectionParameters.getQualifiers(),
                selectionParameters.getQualifyingNames(),
                selectionParameters.getResultType()
            );
        }
    }


    public enum Type {
        PREFER_UPDATE_MAPPING,
        OBJECT_FACTORY,
        LIFECYCLE_CALLBACK,
        PRESENCE_CHECK,
        SOURCE_PARAMETER_CHECK,
        SELF_NOT_ALLOWED,
    }
}
