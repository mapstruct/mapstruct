/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.model.source.builtin.NewDatatypeFactoryConstructorFragment;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A mapping method which is not based on an actual method declared in the original mapper interface but is added as
 * private method to map a certain source/target type combination. Based on a {@link BuiltInMethod}.
 *
 * Specific templates all point to this class, for instance:
 * {@link org.mapstruct.ap.internal.model.source.builtin.XmlGregorianCalendarToCalendar},
 * but also used fields and constructor elements, e.g.
 * {@link org.mapstruct.ap.internal.model.common.FinalField} and
 * {@link NewDatatypeFactoryConstructorFragment}
 *
 * @author Gunnar Morling
 */
public class SupportingMappingMethod extends MappingMethod {

    private final String templateName;
    private final Set<Type> importTypes;
    private final Field supportingField;
    private final SupportingConstructorFragment supportingConstructorFragment;
    private final Map<String, Object> templateParameter;

    public SupportingMappingMethod(BuiltInMethod method, Set<Field> existingFields) {
        super( method );
        this.importTypes = method.getImportTypes();
        this.templateName = getTemplateNameForClass( method.getClass() );
        this.templateParameter = null;
        this.supportingField = getSafeField( method.getFieldReference(), existingFields );
        this.supportingConstructorFragment =
            getSafeConstructorFragment( method.getConstructorFragment(), this.supportingField );
    }

    public SupportingMappingMethod(HelperMethod method, Set<Field> existingFields) {
        super( method );
        this.importTypes = method.getImportTypes();
        this.templateName = getTemplateNameForClass( method.getClass() );
        this.templateParameter = method.getTemplateParameter();
        this.supportingField = getSafeField( method.getFieldReference(), existingFields );
        this.supportingConstructorFragment =
            getSafeConstructorFragment( method.getConstructorFragment(), this.supportingField );
    }

    private Field getSafeField(FieldReference ref, Set<Field> existingFields) {
        if ( ref == null ) {
            return null;
        }

        String name = ref.getVariableName();
        for ( Field existingField : existingFields ) {
            if ( existingField.getVariableName().equals( ref.getVariableName() )
                && existingField.getType().equals( ref.getType() ) ) {
                // field already exists, use that one
                return existingField;
            }
        }
        name = Strings.getSafeVariableName( name, Field.getFieldNames( existingFields ) );
        return new SupportingField( this, ref, name );
    }

    private SupportingConstructorFragment getSafeConstructorFragment(ConstructorFragment fragment,
                                                                     Field supportingField) {
        if ( fragment == null ) {
            return null;
        }

        return new SupportingConstructorFragment(
            this,
            fragment,
            supportingField != null ? supportingField.getVariableName() : null );
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

    public Field getSupportingField() {
        return supportingField;
    }

    public SupportingConstructorFragment getSupportingConstructorFragment() {
        return supportingConstructorFragment;
    }

    public Map<String, Object> getTemplateParameter() {
        return templateParameter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( getName() == null ) ? 0 : getName().hashCode() );
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
        SupportingMappingMethod other = (SupportingMappingMethod) obj;

        if ( !Objects.equals( getName(), other.getName() ) ) {
            return false;
        }

        return true;
    }
}
