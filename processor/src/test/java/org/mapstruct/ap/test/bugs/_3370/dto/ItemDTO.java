/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370.dto;

import java.util.Map;

public abstract class ItemDTO {
    public abstract String getId();

    public abstract Map<String, String> getAttributes();

    public static ImmutableItemDTO.Builder builder() {
        return new Builder();
    }

    public static class Builder extends ImmutableItemDTO.Builder {
        public ImmutableItemDTO.Builder addSomeData(String key, String data) {
            return super.attributes( Map.of( key, data ) );
        }
    }

}
