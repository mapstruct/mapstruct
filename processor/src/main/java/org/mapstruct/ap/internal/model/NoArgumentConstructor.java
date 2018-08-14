/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * Represents a constructor that is used for constructor injection.
 *
 * @author Sjaak Derksen
 */
public class NoArgumentConstructor extends ModelElement implements Constructor {

    private final String name;
    private final Set<SupportingConstructorFragment> fragments;

    public NoArgumentConstructor(String name, Set<SupportingConstructorFragment> fragments) {
        this.name = name;
        this.fragments = fragments;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public String getName() {
        return name;
    }

    public Set<SupportingConstructorFragment> getFragments() {
        return fragments;
    }
}
