/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import javax.tools.Diagnostic.Kind;

/**
 * Policy for reporting issues occurring during the generation of a mapper
 * implementation.
 *
 * @author Gunnar Morling
 */
public enum ReportingPolicy {

    /**
     * No report will be created for the given issue.
     */
    IGNORE,

    /**
     * A report with {@link Kind#WARNING} will be created for the given issue.
     */
    WARN,

    /**
     * A report with {@link Kind#ERROR} will be created for the given issue,
     * causing the compilation to fail.
     */
    ERROR;
}
