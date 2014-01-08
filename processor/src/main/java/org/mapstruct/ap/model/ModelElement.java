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
package org.mapstruct.ap.model;

import java.io.Writer;
import java.util.Set;

/**
 * A model element with the ability to write itself into a given {@link Writer}.
 *
 * @author Gunnar Morling
 */
public interface ModelElement {

    /**
     * Passed to {@link ModelElement}, providing access to additional data
     * specific to a given implementation of the model serialization mechanism.
     *
     * @author Gunnar Morling
     */
    interface Context {

        /**
         * Retrieves the object with the given type from this context.
         *
         * @param type The type of the object to retrieve from this context.
         *
         * @return The object with the given type from this context.
         */
        <T> T get(Class<T> type);
    }

    /**
     * Writes this model element to the given writer.
     *
     * @param context Provides additional data specific to the used implementation
     * of the model serialization mechanism.
     * @param writer The writer to write this model to. Must not be closed by
     * implementations.
     */
    void write(Context context, Writer writer) throws Exception;

    /**
     * Returns a set containing those {@link Type}s referenced by this model
     * element for which an import statement needs to be declared.
     *
     * @return A set with type referenced by this model element. Must not be
     *         {@code null.}
     */
    Set<Type> getImportTypes();
}
