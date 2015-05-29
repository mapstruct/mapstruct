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
package org.mapstruct.ap.internal.model;

import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;

/**
 * Factory class for creating all types of assignments
 *
 * @author Sjaak Derksen
 */
public class AssignmentFactory {

    private AssignmentFactory() {
    }

    public static Assignment createTypeConversion(Set<Type> importTypes, List<Type> exceptionTypes, String expression) {
        return new TypeConversion( importTypes, exceptionTypes, expression );
    }

    public static Assignment createMethodReference(Method method, MapperReference declaringMapper,
                                                   Type targetType) {
        return new MethodReference( method, declaringMapper, targetType );
    }

    public static Assignment createMethodReference(BuiltInMethod method, ConversionContext contextParam) {
        return new MethodReference( method, contextParam );
    }

    public static Direct createDirect(String sourceRef) {
        return new Direct( sourceRef );
    }
}

