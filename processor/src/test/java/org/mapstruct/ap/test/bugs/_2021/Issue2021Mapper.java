/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2021;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
@DecoratedWith(Issue2021Mapper.Decorator.class)
public interface Issue2021Mapper {

    abstract class Decorator implements Issue2021Mapper {

    }

}
