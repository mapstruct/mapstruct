/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.internal.model.common.FieldReference;
import org.mapstruct.ap.internal.model.common.FinalField;
import org.mapstruct.ap.internal.model.common.TypeFactory;

public class GetDateTimeFormatterField extends FinalField implements FieldReference {

    private final String dateFormat;

    public GetDateTimeFormatterField(TypeFactory typeFactory, String dateFormat) {
        super(  typeFactory.getType( DateTimeFormatter.class ), getDateTimeFormatterFieldName( dateFormat ) );
        this.dateFormat = dateFormat;
    }

    @Override
    public Map<String, Object> getTemplateParameter() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put( "dateFormat", dateFormat );
        return parameter;
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
}
