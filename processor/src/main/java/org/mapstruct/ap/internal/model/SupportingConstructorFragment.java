/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * A mapper instance field, initialized as null
 *
 * @author Sjaak Derksen
 */
public class SupportingConstructorFragment extends ModelElement implements ConstructorFragment {

    private final String variableName;
    private final String templateName;
    private final SupportingMappingMethod definingMethod;

    public SupportingConstructorFragment(SupportingMappingMethod definingMethod,
                                         ConstructorFragment constructorFragment, String variableName) {
        this.templateName = getTemplateNameForClass( constructorFragment.getClass() );
        this.definingMethod = definingMethod;
        this.variableName = variableName;
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public SupportingMappingMethod getDefiningMethod() {
        return definingMethod;
    }

    public String getVariableName() {
        return variableName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( variableName == null ) ? 0 : variableName.hashCode() );
        result = prime * result + ( ( templateName == null ) ? 0 : templateName.hashCode() );
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
        SupportingConstructorFragment other = (SupportingConstructorFragment) obj;

        if ( !Objects.equals( variableName, other.variableName ) ) {
            return false;
        }
        if ( !Objects.equals( templateName, other.templateName ) ) {
            return false;
        }
        return true;
    }

    public static void addAllFragmentsIn(Set<SupportingMappingMethod> supportingMappingMethods,
                                         Set<ConstructorFragment> targets) {
        for ( SupportingMappingMethod supportingMappingMethod : supportingMappingMethods ) {
            SupportingConstructorFragment fragment = supportingMappingMethod.getSupportingConstructorFragment();
            if ( fragment != null ) {
                targets.add( supportingMappingMethod.getSupportingConstructorFragment() );
            }
        }
    }

    public static SupportingConstructorFragment getSafeConstructorFragment(SupportingMappingMethod method,
                                                                           ConstructorFragment fragment,
                                                                           Field supportingField) {
        if ( fragment == null ) {
            return null;
        }

        return new SupportingConstructorFragment(
            method,
            fragment,
            supportingField != null ? supportingField.getVariableName() : null );
    }
}
