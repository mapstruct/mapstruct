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
package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.AssignmentFactory;
import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Base class for {@link ConversionProvider}s creating {@link TypeConversion}s which don't declare any exception types.
 *
 * @author Gunnar Morling
 */
public abstract class SimpleConversion implements ConversionProvider {

    @Override
    public Assignment to(ConversionContext conversionContext) {
        String toExpression = getToExpression( conversionContext );
        return AssignmentFactory.createTypeConversion(
            getToConversionImportTypes( conversionContext ),
            Collections.<Type>emptyList(),
            toExpression
        );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        String fromExpression = getFromExpression( conversionContext );
        return AssignmentFactory.createTypeConversion(
            getFromConversionImportTypes( conversionContext ),
            Collections.<Type>emptyList(),
            fromExpression
        );
    }

    /**
     * Returns the conversion string from source to target. The placeholder {@code <SOURCE>} can be used to represent a
     * reference to the source value.
     *
     * @param conversionContext A context providing optional information required for creating the conversion.
     *
     * @return The conversion string from source to target
     */
    protected abstract String getToExpression(ConversionContext conversionContext);

    /**
     * Returns the conversion string from target to source. The placeholder {@code <SOURCE>} can be used to represent a
     * reference to the target value.
     *
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return The conversion string from target to source
     */
    protected abstract String getFromExpression(ConversionContext conversionContext);

    /**
     * Returns a set with imported types of the "from" conversion. Defaults to an empty set; can be overridden in
     * sub-classes to return the required types.
     *
     * @param conversionContext the conversion context
     *
     * @return conversion types required in the "from" conversion
     */
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.<Type>emptySet();
    }

    /**
     * Returns a set with imported types of the "to" conversion. Defaults to an empty set; can be overridden in
     * sub-classes to return the required types.
     *
     * @param conversionContext the conversion context
     *
     * @return conversion types required in the "to" conversion
     */
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.<Type>emptySet();
    }
}
