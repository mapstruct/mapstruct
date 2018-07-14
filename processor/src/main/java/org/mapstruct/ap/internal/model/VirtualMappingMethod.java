/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;

/**
 * A mapping method which is not based on an actual method declared in the original mapper interface but is added as
 * private method to map a certain source/target type combination. Based on a {@link BuiltInMethod}.
 *
 * @author Gunnar Morling
 */
public class VirtualMappingMethod extends MappingMethod {

    private final String templateName;
    private final Set<Type> importTypes;

    public VirtualMappingMethod(BuiltInMethod method) {
        super( method );
        this.importTypes = method.getImportTypes();
        this.templateName = getTemplateNameForClass( method.getClass() );
    }

    public VirtualMappingMethod(HelperMethod method) {
        super( method );
        this.importTypes = method.getImportTypes();
        this.templateName = getTemplateNameForClass( method.getClass() );
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    /**
     * Finds a {@link Type} by a given name. The {@code name} will be compared to the fully-qualified and also simple
     * names of the {@code importTypes}.
     *
     * @param name Fully-qualified or simple name of the type.
     *
     * @return Found type, never <code>null</code>.
     *
     * @throws IllegalArgumentException In case no {@link Type} was found for given name.
     */
    public Type findType(String name) {
        for ( Type type : importTypes ) {
            if ( type.getFullyQualifiedName().contentEquals( name ) ) {
                return type;
            }
            if ( type.getName().contentEquals( name ) ) {
                return type;
            }
        }

        throw new IllegalArgumentException( "No type for given name '" + name + "' found in 'importTypes'." );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        VirtualMappingMethod other = (VirtualMappingMethod) obj;
        if ( templateName == null ) {
            if ( other.templateName != null ) {
                return false;
            }
        }
        else if ( !templateName.equals( other.templateName ) ) {
            return false;
        }
        return true;
    }
}
