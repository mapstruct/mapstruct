/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3591;

import java.util.Map;
import java.util.stream.Stream;

public class ContainerBeanDto {

    private String value;
    private Map<String, ContainerBeanDto> beanMap;
    private Stream<ContainerBeanDto> beanStream;

    public Map<String, ContainerBeanDto> getBeanMap() {
        return beanMap;
    }

    public void setBeanMap(Map<String, ContainerBeanDto> beanMap) {
        this.beanMap = beanMap;
    }

    public Stream<ContainerBeanDto> getBeanStream() {
        return beanStream;
    }

    public void setBeanStream(Stream<ContainerBeanDto> beanStream) {
        this.beanStream = beanStream;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
