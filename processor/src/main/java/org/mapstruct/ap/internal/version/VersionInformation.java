/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.version;

/**
 * Provides information about the processor version and the processor context implementation version
 *
 * @author Andreas Gudian
 */
public interface VersionInformation {
    String getRuntimeVersion();

    String getRuntimeVendor();

    String getMapStructVersion();

    String getCompiler();

    boolean isSourceVersionAtLeast9();

    boolean isSourceVersionAtLeast19();

    boolean isEclipseJDTCompiler();

    boolean isJavacCompiler();
}
