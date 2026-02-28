/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

/**
 * {@link ReadAccessor} that delegates to another {@link Accessor} and requires an implementation of
 * {@link #getSimpleName()}
 *
 * @author Filip Hrisafov
 */
public abstract class ReadDelegateAccessor extends DelegateAccessor implements ReadAccessor {

    protected ReadDelegateAccessor(Accessor delegate) {
        super( delegate );
    }

}
