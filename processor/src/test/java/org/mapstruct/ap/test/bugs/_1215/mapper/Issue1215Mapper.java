/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1215.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._1215.dto.EntityDTO;
import org.mapstruct.ap.test.bugs._1215.entity.Entity;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1215Mapper {
    Entity fromDTO(EntityDTO dto);
}
