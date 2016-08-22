/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.sql.Time;
import java.util.Date;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;


/**
 * @author Filip Hrisafov
 */
public class DateToSqlTime extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public DateToSqlTime(TypeFactory typeFactory) {
        this.parameter = new Parameter( "date", typeFactory.getType( Date.class ) );
        this.returnType = typeFactory.getType( Time.class );

        this.importTypes = asSet(
            parameter.getType(),
            returnType
        );
    }

    @Override
    public boolean doTypeVarsMatch(Type parameter, Type returnType) {
        //We must make sure that there is only a match when the types are equal. The reason for this is to avoid
        //ambiguous problems, when the return type is java.util.Date
        return getReturnType().equals( returnType );
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
}
