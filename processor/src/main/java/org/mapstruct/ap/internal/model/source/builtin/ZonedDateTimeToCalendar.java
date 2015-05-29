/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.JavaTimeConstants;

/**
 * {@link BuiltInMethod} for mapping between {@link Calendar} and {@link ZonedDateTime}.
 * <p>
 * Template is at org.mapstruct.ap.model.builtin.ZonedDateTimeToCalendar.ftl
 */
public class ZonedDateTimeToCalendar extends BuiltInMethod {
    private final Type returnType;
    private final Parameter parameter;
    private final Set<Type> importedTypes;

    ZonedDateTimeToCalendar(TypeFactory typeFactory) {
        this.returnType = typeFactory.getType( Calendar.class );
        this.parameter = new Parameter( "dateTime", typeFactory.getType( JavaTimeConstants.ZONED_DATE_TIME_FQN ) );
        this.importedTypes = Collections.asSet( returnType, parameter.getType() );
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
    public Set<Type> getImportTypes() {
        return importedTypes;
    }
}
