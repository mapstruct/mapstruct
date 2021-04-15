/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.FieldReference;

/**
 * Implementations create inline {@link TypeConversion}s such as
 *
 * <ul>
 * <li>{@code (long)source},</li>
 * <li>{@code Integer.valueOf(source)} or</li>
 * <li>{@code new SimpleDateFormat().format( source )}.</li>
 * </ul>
 *
 * @author Gunnar Morling
 */
public interface ConversionProvider {

    /**
     * Creates the conversion from source to target of a property mapping.
     *
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return A conversion from source to target.
     */
    Assignment to(ConversionContext conversionContext);

    /**
     * Creates the conversion from target to source of a property mapping.
     *
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return A conversion from target to source.
     */
    Assignment from(ConversionContext conversionContext);

    /**
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return any helper methods when required.
     */
    List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext);

    /**
     * @param conversionContext ConversionContext providing optional information required for creating the conversion.
     *
     * @return any fields when required.
     */
    default List<FieldReference> getRequiredHelperFields(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

}
