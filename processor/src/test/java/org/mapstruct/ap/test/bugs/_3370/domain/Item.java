/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370.domain;

import java.util.Collections;
import java.util.Map;

public abstract class Item {

    public abstract String getId();

    public abstract Map<String, String> getAttributes();

    public static Item.Builder builder() {
        return new Item.Builder();
    }

    public static class Builder extends ImmutableItem.Builder {

        public ImmutableItem.Builder addSomeData(String key, String data) {
            return super.attributes( Collections.singletonMap( key, data ) );
        }
    }
}
