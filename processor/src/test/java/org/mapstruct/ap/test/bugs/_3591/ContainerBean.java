package org.mapstruct.ap.test.bugs._3591;

import java.util.Map;
import java.util.stream.Stream;

public class ContainerBean {

    private String value;
    private Map<String, ContainerBean> beanMap;
    private Stream<ContainerBean> beanStream;

    public ContainerBean() {
    }

    public ContainerBean(String value) {
        this.value = value;
    }

    public Map<String, ContainerBean> getBeanMap() {
        return beanMap;
    }

    public void setBeanMap(Map<String, ContainerBean> beanMap) {
        this.beanMap = beanMap;
    }

    public Stream<ContainerBean> getBeanStream() {
        return beanStream;
    }

    public void setBeanStream(Stream<ContainerBean> beanStream) {
        this.beanStream = beanStream;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
