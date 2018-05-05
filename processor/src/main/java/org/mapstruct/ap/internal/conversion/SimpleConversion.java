/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Assignment.AssignmentMessage;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;
import org.mapstruct.ap.internal.util.Message;

/**
 * Base class for {@link ConversionProvider}s creating {@link TypeConversion}s which don't declare any exception types.
 *
 * @author Gunnar Morling
 */
public abstract class SimpleConversion implements ConversionProvider {

    @Override
    public Assignment to(ConversionContext conversionContext) {
        String toExpression = getToExpression( conversionContext );
        return new TypeConversion( getToConversionImportTypes( conversionContext ),
            getToConversionExceptionTypes( conversionContext ),
            toExpression,
            getLossyConversionMessage( conversionContext )
        );
    }

    @Override
    public Assignment from(ConversionContext conversionContext) {
        String fromExpression = getFromExpression( conversionContext );
        return new TypeConversion( getFromConversionImportTypes( conversionContext ),
            getFromConversionExceptionTypes( conversionContext ),
            fromExpression,
            getLossyConversionMessage( conversionContext )
        );
    }

    @Override
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return Collections.emptyList();
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
        return Collections.emptySet();
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
        return Collections.emptySet();
    }

    protected List<Type> getToConversionExceptionTypes(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

    protected AssignmentMessage getLossyConversionMessage( ConversionContext ctx ) {

        AssignmentMessage message = null;
        if ( isNarrowing() ) {
            if ( ctx.getTypeConversionPolicy() == ReportingPolicyPrism.WARN ) {
                message = new AssignmentMessage( Message.CONVERSION_LOSSY_WARNING,
                    ctx.getSourceErrorMessagePart(), ctx.getSourceType().toString(), ctx.getTargetType().toString() );
            }
            else if ( ctx.getTypeConversionPolicy() == ReportingPolicyPrism.ERROR ) {
                message = new AssignmentMessage( Message.CONVERSION_LOSSY_ERROR,
                    ctx.getSourceErrorMessagePart(), ctx.getSourceType().toString(), ctx.getTargetType().toString() );
            }
        }
        return message;
    }

    @Override
    public boolean isNarrowing() {
        return false;
    }
}
