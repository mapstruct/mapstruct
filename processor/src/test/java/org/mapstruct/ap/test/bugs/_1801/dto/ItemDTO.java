/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801.dto;

import java.util.Map;

/**
 * @author Zhizhi Deng
 */
public abstract class ItemDTO {
    public abstract String getId();

    public abstract Map<String, String> getAttributes();

}
