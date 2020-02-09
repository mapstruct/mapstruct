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
    // Current tycho-compiler-jdt (0.26.0) is not compatible with Java 11
    // Updating to latest version 1.6.0 fails some tests
    // Once https://github.com/mapstruct/mapstruct/pull/1587 is resolved we can remove the max JRE
    ECLIPSE( JRE.JAVA_8 );

    private final JRE max;

    Compiler() {
        this( JRE.OTHER );
    }

    Compiler(JRE max) {
        this.max = max;
    }

    public JRE maxJre() {
        return max;
    }
}
