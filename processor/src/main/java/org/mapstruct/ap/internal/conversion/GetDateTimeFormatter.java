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

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.util.Strings;

/**
 * HelperMethod that returns a static {@link DateTimeFormatter} instance for given dateFormat (or
 * defaultFormatterSuffix).
 *
 * @author Ewald Volkert
 */
public class GetDateTimeFormatter extends HelperMethod {

    private final Type returnType;
    private final Set<Type> importTypes;

    private final String name;
    private final String dateFormat;
    private final String defaultFormatterSuffix;

    public GetDateTimeFormatter(TypeFactory typeFactory, String dateFormat, String defaultFormatterSuffix) {
        this.returnType = typeFactory.getType( DateTimeFormatter.class );
        this.importTypes = asSet( returnType );
        this.dateFormat = Strings.isEmpty( dateFormat ) ? null : dateFormat;
        this.defaultFormatterSuffix = defaultFormatterSuffix;
        this.name = getDateTimeFormatterMethodName( dateFormat, defaultFormatterSuffix );
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
        parameter.put( "defaultFormatterSuffix", defaultFormatterSuffix );
        parameter.put( "className", Strings.capitalize( name ) );
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

    public static String getDateTimeFormatterMethodName(String dateFormat, String defaultFormatterSuffix) {
        String name;
        if ( !Strings.isEmpty( dateFormat ) ) {
            name = "getDateTimeFormatterWithPattern_" + safeMethodNamePart( dateFormat );
        }
        else {
            name = "getDateTimeFormatterWithSuffix_" + safeMethodNamePart( defaultFormatterSuffix );
        }

        return name;
    }

    private static String safeMethodNamePart(String dateFormat) {
        StringBuilder sb = new StringBuilder();

        dateFormat.codePoints().forEach( cp -> {
            if ( Character.isJavaIdentifierPart( cp ) ) {
                // safe to character to method name as is
                sb.append( Character.toChars( cp ) );
            }
            else {
                // could not be used in method name
                // thus just show code point instead with some pre/post fix
                sb.append( "$" ).append( cp ).append( "_" );
            }
        } );

        return sb.toString();
    }
}
