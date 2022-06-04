/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2867;

import org.mapstruct.MappingTarget;

/**
 * @author Filip Hrisafov
 */
public interface Issue2867BaseMapper<T, S> {

    void update(@MappingTarget T target, S source);

}
