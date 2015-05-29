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
package org.mapstruct.ap.internal.model.common;

import java.io.Writer;
import java.util.Set;

import org.mapstruct.ap.internal.writer.FreeMarkerModelElementWriter;
import org.mapstruct.ap.internal.writer.FreeMarkerWritable;
import org.mapstruct.ap.internal.writer.Writable;

/**
 * Base class of all model elements. Implements the {@link Writable} contract to write model elements into source code
 * files.
 *
 * @author Gunnar Morling
 */
public abstract class ModelElement extends FreeMarkerWritable {

    @Override
    public void write(Context context, Writer writer) throws Exception {
        new FreeMarkerModelElementWriter().write( this, context, writer );
    }

    /**
     * Returns a set containing those {@link Type}s referenced by this model element for which an import statement needs
     * to be declared.
     *
     * @return A set with type referenced by this model element. Must not be {@code null}.
     */
    public abstract Set<Type> getImportTypes();
}
