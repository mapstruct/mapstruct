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
package org.mapstruct.ap.conversion;

import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.model.Assignment;
import org.mapstruct.ap.model.assignment.AssignmentFactory;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.assignment.TypeConversion;
import org.mapstruct.ap.model.common.Type;

/**
 * Base class for {@link ConversionProvider}s creating {@link TypeConversion}s which don't declare any exception types.
 *
 * @author Gunnar Morling
 */
public abstract class SimpleConversion implements ConversionProvider {

    @Override
    public Assignment to(ConversionContext conversionContext) {
        ConversionExpression toExpressions = getToExpression( conversionContext );
        return AssignmentFactory.createTypeConversion(
                getToConversionImportTypes( conversionContext ),
                Collections.<Type>emptyList(),
                toExpressions.getOpenExpression(),
                toExpressions.getCloseExpression() );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        ConversionExpression fromExpressions = getFromExpression( conversionContext );
        return AssignmentFactory.createTypeConversion(
                getFromConversionImportTypes( conversionContext ),
                Collections.<Type>emptyList(),
                fromExpressions.getOpenExpression(),
                fromExpressions.getCloseExpression() );
    }

    /**
     * Returns the conversion string (opening and closing part) from source to target.
     *
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return The open- and close parts of the conversion expression
     */
    protected abstract ConversionExpression getToExpression( ConversionContext conversionContext );

    /**
     * Creates the conversion string (opening and closing part) from target to source.
     *
     * @param conversionContext ConversionContext providing optional information required for creating
     * the conversion.
     *
     * @return The open- and close parts of the conversion expression
     */
    protected abstract ConversionExpression getFromExpression( ConversionContext conversionContext );

    /**
     * Returns a set with imported types of the "from" conversion. Defaults to an empty set; can be overridden in
     * sub-classes to return the required types.
     *
     * @param conversionContext the conversion context
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
     * @return conversion types required in the "to" conversion
     */
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.<Type>emptySet();
    }

}
