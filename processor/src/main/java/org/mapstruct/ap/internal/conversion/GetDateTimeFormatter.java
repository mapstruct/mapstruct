/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperConstructorFragment;
import org.mapstruct.ap.internal.model.HelperFieldReference;
import org.mapstruct.ap.internal.model.HelperFinalField;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Pseudo-HelperMethod that creates no method at all, but initializes a {@link DateTimeFormatter} instance with given
 * dateFormat as mapper field
 *
 * @author Ewald Volkert
 */
public class GetDateTimeFormatter extends HelperMethod {

    private final Type returnType;
    private final Set<Type> importTypes;

    private final String name;
    private final String dateFormat;

    private final HelperFinalField fieldReference;
    private final GetDateTimeFormatterConstructorFragment constructorFragment;

    public GetDateTimeFormatter(TypeFactory typeFactory, String dateFormat) {
        this.returnType = typeFactory.getType( DateTimeFormatter.class );
        this.importTypes = asSet( typeFactory.getType( DateTimeFormatter.class ) );
        this.dateFormat = Strings.isEmpty( dateFormat ) ? null : dateFormat;
        this.name = getDateTimeFormatterFieldName( dateFormat );

        this.fieldReference =
            new HelperFinalField( typeFactory.getType( DateTimeFormatter.class ), this.name );
        this.constructorFragment = new GetDateTimeFormatterConstructorFragment();
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public Parameter getParameter() {
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return Collections.emptyList();
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public MappingMethodOptions getOptions() {
        return MappingMethodOptions.empty();
    }

    @Override
    public String describe() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getTemplateParameter() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put( "dateFormat", dateFormat );
        return parameter;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals( obj ) ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        GetDateTimeFormatter other = (GetDateTimeFormatter) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        }
        else if ( !name.equals( other.name ) ) {
            return false;
        }
        return true;
    }

    public static String getDateTimeFormatterFieldName(String dateFormat) {
        StringBuilder sb = new StringBuilder();
        sb.append( "dateTimeFormatter_" );

        dateFormat.codePoints().forEach( cp -> {
            if ( Character.isJavaIdentifierPart( cp ) ) {
                // safe to character to method name as is
                sb.append( Character.toChars( cp ) );
            }
            else {
                // could not be used in method name
                sb.append( "_" );
            }
        } );

        sb.append( "_" );

        int hashCode = dateFormat.hashCode();
        sb.append( hashCode < 0 ? "0" : "1" );
        sb.append( Math.abs( hashCode ) );

        return sb.toString();
    }

    @Override
    public HelperFieldReference getFieldReference() {
        return fieldReference;
    }

    @Override
    public HelperConstructorFragment getConstructorFragment() {
        return constructorFragment;
    }

    public static class GetDateTimeFormatterConstructorFragment implements HelperConstructorFragment {

    }
}
