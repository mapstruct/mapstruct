/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
