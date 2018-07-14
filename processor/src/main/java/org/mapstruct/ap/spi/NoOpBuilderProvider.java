/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

// tag::documentation[]

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

// end::documentation[]

/**
 * A NoOp {@link BuilderProvider} which returns {@code null} when searching for a builder.
 *
 * @author Filip Hrisafov
 */
// tag::documentation[]
public class NoOpBuilderProvider implements BuilderProvider {

    @Override
    public BuilderInfo findBuilderInfo(TypeMirror type, Elements elements, Types types) {
        return null;
    }
}
// end::documentation[]
