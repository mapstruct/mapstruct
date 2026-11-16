/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.kotlin;

import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Information about a type in case it's a Kotlin type.
 *
 * @author Filip Hrisafov
 */
public interface KotlinMetadata {

    boolean isDataClass();

    boolean isSealedClass();

    ExecutableElement determinePrimaryConstructor(List<ExecutableElement> constructors);

    List<? extends TypeMirror> getPermittedSubclasses();
}
