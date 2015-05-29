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
package org.mapstruct.ap.internal.processor;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.version.VersionInformation;

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
public interface ModelElementProcessor<P, R> {

    /**
     * Context object passed to
     * {@link ModelElementProcessor#process(ProcessorContext, TypeElement, Object)}
     * providing access to common infrastructure objects such as {@link Types}
     * etc.
     *
     * @author Gunnar Morling
     */
    public interface ProcessorContext {

        Filer getFiler();

        Types getTypeUtils();

        Elements getElementUtils();

        TypeFactory getTypeFactory();

        FormattingMessager getMessager();

        Options getOptions();

        VersionInformation getVersionInformation();

        /**
         * Whether the currently processed mapper type is erroneous which is the
         * case if at least one diagnostic with {@link Kind#ERROR} is reported
         * by any of the participating processors.
         *
         * @return {@code true} if the currently processed mapper type is
         *         erroneous, {@code false} otherwise.
         */
        boolean isErroneous();
    }

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

    /**
     * Returns the priority value of this processor which must be between 1
     * (highest priority) and 10000 (lowest priority). Processors are invoked in
     * order from highest to lowest priority, starting with the mapping method
     * retrieval processor (priority 1) and finishing with the code generation
     * processor (priority 10000). Processors working on the built
     * {@code Mapper} model must have a priority &gt; 1000.
     *
     * @return The priority value of this processor.
     */
    int getPriority();
}
