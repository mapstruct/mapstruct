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
 * Base interface for all ModelElementProcessor's.
 *
 * @author Gunnar Morling
 */
public interface ModelElementProcessor {

    /**
     * Context object passed to
     * {@link MapperElementProcessor#process(ProcessorContext, TypeElement, Object)}
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
     * Returns the priority value of this processor which must be between 1
     * (highest priority) and 10000 (lowest priority). Processors are invoked in
     * order from lowest to highest priority, starting with the mapping method
     * retrieval processor (priority 1) and finishing with the code generation
     * processor (priority 10000). Processors working on the built
     * {@code Mapper} model must have a priority &gt; 1000.
     *
     * @return The priority value of this processor.
     */
    int getPriority();
}
