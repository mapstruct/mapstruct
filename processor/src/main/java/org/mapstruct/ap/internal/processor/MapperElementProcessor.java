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
package org.mapstruct.ap.internal.processor;

import javax.lang.model.element.TypeElement;

/**
 * A processor which performs one task of the mapper generation, e.g. retrieving
 * methods from the source {@link TypeElement}, performing validity checks or
 * generating the output source file.
 *
 * @param <P> The parameter type processed by this processor
 * @param <R> The return type created by this processor
 *
 * @author Gunnar Morling
 */
public interface MapperElementProcessor<P, R> extends ModelElementProcessor {

    /**
     * Processes the given source element, representing a Java bean mapper in
     * one form or another.
     *
     * @param context Context providing common infrastructure objects.
     * @param mapperTypeElement The original type element from which the given mapper object
     * is derived.
     * @param sourceModel The current representation of the bean mapper. Never
     * {@code null} (the very first processor receives the original
     * type element).
     *
     * @return The resulting representation of the bean mapper; may be the same
     *         as the source representation, e.g. if a given implementation just
     *         performs some sort of validity check. Implementations must never
     *         return {@code null} except for the very last processor which
     *         generates the resulting Java source file.
     */
    R process(ProcessorContext context, TypeElement mapperTypeElement, P sourceModel);


}
