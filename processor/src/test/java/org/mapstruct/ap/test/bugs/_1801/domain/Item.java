/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801.domain;

/**
 * @author Zhizhi Deng
 */
public abstract class Item {
    public abstract String getId();

    public static class Builder extends ImmutableItem.Builder {
    }
}
