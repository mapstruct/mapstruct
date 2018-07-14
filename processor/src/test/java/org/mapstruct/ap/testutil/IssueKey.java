/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Id of the issue addressed by a test or test method in the <a
 * href="https://github.com/mapstruct/mapstruct/issues">issue tracker</a>.
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.SOURCE)
public @interface IssueKey {

    /**
     * The issue number.
     *
     * @return the issue number
     */
    String value();
}
