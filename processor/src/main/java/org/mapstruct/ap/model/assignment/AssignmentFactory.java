/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model.assignment;

import java.util.List;
import java.util.Set;
import org.mapstruct.ap.model.Assignment;
import org.mapstruct.ap.model.FactoryMethod;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.builtin.BuiltInMethod;

/**
 * Factory class for creating all types of assignments
 *
 * @author Sjaak Derksen
 */
public class AssignmentFactory {

    private AssignmentFactory() {
    }

    public static Assignment createTypeConversion( Set<Type> importTypes,
            List<Type> exceptionTypes,
            String openExpression,
            String closeExpression ) {
        return new TypeConversion( importTypes, exceptionTypes, openExpression, closeExpression );
    }

    public static FactoryMethod createFactory(SourceMethod method, MapperReference declaringMapper) {
        return  new MethodReference( method, declaringMapper, null );
    }

    public static Assignment createMethodReference(SourceMethod method, MapperReference declaringMapper,
            Type targetType) {
        return new  MethodReference(method, declaringMapper, targetType);
    }

    public static Assignment createMethodReference( BuiltInMethod method, ConversionContext contextParam ) {
        return new MethodReference( method, contextParam );
    }

    public static Simple createSimple( String sourceRef ) {
        return new Simple(sourceRef );
    }

}
