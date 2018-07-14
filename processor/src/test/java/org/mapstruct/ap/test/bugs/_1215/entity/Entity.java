/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1215.entity;

/**
 * @author Filip Hrisafov
 */
public class Entity {
    private Tag[] tags;
    private AnotherTag[][] otherTags;

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public AnotherTag[][] getOtherTags() {
        return otherTags;
    }

    public void setOtherTags(AnotherTag[][] otherTags) {
        this.otherTags = otherTags;
    }
}
