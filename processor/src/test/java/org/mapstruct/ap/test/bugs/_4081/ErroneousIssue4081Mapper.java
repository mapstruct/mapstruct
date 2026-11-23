/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4081;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;

@Mapper
@NullMarked
public interface ErroneousIssue4081Mapper {
    Target map(Source source);

    default Target.@Nullable Payload mapPayload(String payload) {
        if ( payload.isBlank() ) {
            return null;
        }
        return new Target.Payload( payload );
    }
}
