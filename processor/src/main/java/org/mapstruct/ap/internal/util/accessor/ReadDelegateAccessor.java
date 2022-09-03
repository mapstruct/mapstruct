/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

/**
 * @author Filip Hrisafov
 */
public abstract class ReadDelegateAccessor extends DelegateAccessor implements ReadAccessor {

    protected ReadDelegateAccessor(Accessor delegate) {
        super( delegate );
    }

}
