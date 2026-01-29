/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.kotlin;

import java.util.List;
import javax.lang.model.element.ExecutableElement;

/**
 * Information about a type in case it's a Kotlin type.
 *
 * @author Filip Hrisafov
 */
public interface KotlinMetadata {

    boolean isDataClass();

    ExecutableElement determinePrimaryConstructor(List<ExecutableElement> constructors);
}
