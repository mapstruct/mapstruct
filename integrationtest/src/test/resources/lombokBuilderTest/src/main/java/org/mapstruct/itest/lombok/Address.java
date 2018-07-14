/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.lombok;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Address {
    private final String addressLine;
}
