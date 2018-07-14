/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.testutil.runner;

/**
 * Describes a required entry in the Maven toolchains.xml file.
 *
 * @author Andreas Gudian
 */
class Toolchain {
    private final String vendor;
    private final String versionMinInclusive;
    private final String versionMaxExclusive;

    Toolchain(String vendor, String versionMinInclusive, String versionMaxExclusive) {
        this.vendor = vendor;
        this.versionMinInclusive = versionMinInclusive;
        this.versionMaxExclusive = versionMaxExclusive;
    }

    String getVendor() {
        return vendor;
    }

    String getVersionMinInclusive() {
        return versionMinInclusive;
    }

    /**
     * @return the version range string to be used in the Maven execution to select the toolchain
     */
    String getVersionRangeString() {
        return "[" + versionMinInclusive + "," + versionMaxExclusive + ")";
    }
}
