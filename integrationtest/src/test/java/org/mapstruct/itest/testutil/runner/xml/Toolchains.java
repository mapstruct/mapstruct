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
package org.mapstruct.itest.testutil.runner.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB representation of some of the parts in the Maven toolchains.xml file.
 *
 * @author Andreas Gudian
 */
@XmlRootElement
public class Toolchains {
    @XmlElement(name = "toolchain")
    private List<Toolchain> toolchains = new ArrayList<>();

    public List<Toolchain> getToolchains() {
        return toolchains;
    }

    @Override
    public String toString() {
        return "Toolchains [toolchains=" + toolchains + "]";
    }

    public static class Toolchain {
        @XmlElement
        private String type;

        @XmlElement(name = "provides")
        private ProviderDescription providerDescription;

        public String getType() {
            return type;
        }

        public ProviderDescription getProviderDescription() {
            return providerDescription;
        }

        @Override
        public String toString() {
            return "Toolchain [type=" + type + ", providerDescription=" + providerDescription + "]";
        }
    }

    public static class ProviderDescription {
        @XmlElement
        private String version;

        @XmlElement
        private String vendor;

        public String getVersion() {
            return version;
        }

        public String getVendor() {
            return vendor;
        }

        @Override
        public String toString() {
            return "ProviderDescription [version=" + version + ", vendor=" + vendor + "]";
        }
    }
}
