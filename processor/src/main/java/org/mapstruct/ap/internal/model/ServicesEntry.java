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
 * @author Christophe Labouisse on 14/07/2015.
 */
public class ServicesEntry extends ModelElement {
    private final String packageName;

    private final String name;

    private final String implementationPackage;

    private final String implementationName;

    public ServicesEntry(String packageName, String name, String implementationPackage, String implementationName) {
        this.packageName = packageName;
        this.name = name;
        this.implementationPackage = implementationPackage;
        this.implementationName = implementationName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getImplementationPackage() {
        return implementationPackage;
    }

    public String getImplementationName() {
        return implementationName;
    }
}
