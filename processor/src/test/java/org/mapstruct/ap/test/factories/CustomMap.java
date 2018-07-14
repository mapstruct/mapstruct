/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import java.util.Map;

/**
 * @author Sjaak Derksen
 */
public interface CustomMap<K, V> extends Map<K, V> {

    String getTypeProp();
}
