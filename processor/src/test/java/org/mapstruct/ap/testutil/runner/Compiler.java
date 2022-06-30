/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import org.junit.jupiter.api.condition.JRE;

/**
 * @author Andreas Gudian
 * @author Filip Hrisafov
 */
public enum Compiler {
    JDK,
    ECLIPSE;

    private final JRE latestSupportedJre;

    Compiler() {
        this( JRE.OTHER );
    }

    Compiler(JRE latestSupportedJre) {
        this.latestSupportedJre = latestSupportedJre;
    }

    public JRE latestSupportedJre() {
        return latestSupportedJre;
    }
}
