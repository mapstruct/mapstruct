/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/**
 * Accesor naming strategy for Immutables.
 * The generated Immutables also have a from that works as a copy. Our default strategy considers this method
 * as a setter with a name {@code from}. Therefore we are ignoring it.
 *
 * @author Filip Hrisafov
 */
public class ImmutablesAccessorNamingStrategy extends DefaultAccessorNamingStrategy {

    @Override
    protected boolean isBuilderSetter(ExecutableElement method) {
        return super.isBuilderSetter( method ) && !method.getSimpleName().toString().equals( "from" );
    }
}
