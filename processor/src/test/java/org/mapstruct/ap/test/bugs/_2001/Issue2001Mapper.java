/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2001;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2001Mapper {

    Form map(Entity entity);
}
