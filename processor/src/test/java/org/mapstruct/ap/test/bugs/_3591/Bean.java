package org.mapstruct.ap.test.bugs._3591;

import java.util.List;

public class Bean {
    private List<Bean> beans;
    private String value;

    public Bean() {
    }

    public Bean(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }
}
