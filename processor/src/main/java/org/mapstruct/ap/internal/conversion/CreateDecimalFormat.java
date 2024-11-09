/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * HelperMethod that creates a {@link java.text.DecimalFormat}
 *
 * {@code DecimalFormat df = new DecimalFormat( numberFormat )}
 * with setParseBigDecimal set to true.
 *
 * @author Sjaak Derksen
 */
public class CreateDecimalFormat extends HelperMethod {

    private final Parameter parameter;
    private final Parameter localeParameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public CreateDecimalFormat(TypeFactory typeFactory, boolean withLocale) {
        this.parameter = new Parameter( "numberFormat", typeFactory.getType( String.class ) );
        this.localeParameter = withLocale ? new Parameter( "locale", typeFactory.getType( Locale.class ) ) : null;
        this.returnType = typeFactory.getType( DecimalFormat.class );
        if ( withLocale ) {
            this.importTypes = asSet(
                parameter.getType(),
                returnType,
                typeFactory.getType( DecimalFormatSymbols.class ),
                typeFactory.getType( Locale.class )
            );
        }
        else {
            this.importTypes = asSet( parameter.getType(), returnType );
        }
    }

    @Override
    public String getName() {
        return localeParameter == null ? "createDecimalFormat" : "createDecimalFormatWithLocale";
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
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
    public List<Parameter> getParameters() {
        if ( localeParameter == null ) {
            return super.getParameters();
        }
        return Arrays.asList( getParameter(), localeParameter );
    }
}
