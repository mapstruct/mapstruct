/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Set;

import org.mapstruct.ap.internal.writer.FreeMarkerWritable;
import org.mapstruct.ap.internal.writer.Writable;

/**
 * Base class of all model elements. Implements the {@link Writable} contract to write model elements into source code
 * files.
 *
 * @author Gunnar Morling
 */
public abstract class ModelElement extends FreeMarkerWritable {

    /**
     * Returns a set containing those {@link Type}s referenced by this model element for which an import statement needs
     * to be declared.
     *
     * @return A set with type referenced by this model element. Must not be {@code null}.
     */
    public abstract Set<Type> getImportTypes();
}
