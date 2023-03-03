/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

public interface BaseOptionalTestMapper {

    Target toTarget(Source source);

    Source fromTarget(Target target);

}
