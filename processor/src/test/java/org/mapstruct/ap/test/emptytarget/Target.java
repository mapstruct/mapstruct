/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.emptytarget;

/**
 * @author Filip Hrisafov
 */
public class Target {

    private final String label;
    private final double weight;
    private final Object content;

    public Target(String label, double weight, Object content) {
        this.label = label;
        this.weight = weight;
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public double getWeight() {
        return weight;
    }

    public Object getContent() {
        return content;
    }
}
