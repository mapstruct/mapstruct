/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.conversion;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.text.DecimalFormat;
import java.util.Set;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;

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
    private final Type returnType;
    private final Set<Type> importTypes;

    public CreateDecimalFormat(TypeFactory typeFactory) {
        this.parameter = new Parameter( "numberFormat", typeFactory.getType( String.class ) );
        this.returnType = typeFactory.getType( DecimalFormat.class );
        this.importTypes = asSet( parameter.getType(), returnType );
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
    public MappingOptions getMappingOptions() {
        return MappingOptions.empty();
    }
}
