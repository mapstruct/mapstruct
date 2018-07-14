/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._543;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._543.dto.Source;
import org.mapstruct.ap.test.bugs._543.dto.Target;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = { SourceUtil.class })
public interface Issue543Mapper {

    List<Target> map(List<? extends Source> source);
}
