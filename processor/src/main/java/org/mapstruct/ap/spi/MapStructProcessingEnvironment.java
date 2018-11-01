/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * MapStruct will provide the implementations of its SPIs with on object implementing this interface so they can use
 * facilities provided by it. It is a subset of {@link javax.annotation.processing.ProcessingEnvironment
 * ProcessingEnvironment}.
 *
 * @author Filip Hrisafov
 * @see javax.annotation.processing.ProcessingEnvironment
 */
public interface MapStructProcessingEnvironment {

    /**
     * Returns an implementation of some utility methods for
     * operating on elements
     *
     * @return element utilities
     */
    Elements getElementUtils();

    /**
     * Returns an implementation of some utility methods for
     * operating on types.
     *
     * @return type utilities
     */
    Types getTypeUtils();

}
