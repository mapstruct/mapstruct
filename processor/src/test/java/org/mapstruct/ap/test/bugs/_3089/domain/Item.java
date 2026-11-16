/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3089.domain;

import java.util.Map;

/**
 * @author Oliver Erhart
 */
public abstract class Item {
    public abstract String getId();

    public abstract Map<String, String> getAttributes();

    public static class Builder extends ImmutableItem.Builder { }
}
