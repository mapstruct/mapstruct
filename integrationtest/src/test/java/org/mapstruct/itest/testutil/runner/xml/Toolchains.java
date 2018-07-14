/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
