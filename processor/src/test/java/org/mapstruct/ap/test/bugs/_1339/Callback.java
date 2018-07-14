/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1339;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;

/**
 * @author Filip Hrisafov
 */
public class Callback {

    @AfterMapping
    public void afterMapping(@MappingTarget Issue1339Mapper.Target target, @Context int primitive) {
        target.otherField = primitive;
    }
}
