/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sjaak Derksen
 */
public class TestMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    private static boolean puttAllCalled = false;

    public static boolean isPuttAllCalled() {
        return puttAllCalled;
    }

    public static void setPuttAllCalled(boolean puttAllCalled) {
        TestMap.puttAllCalled = puttAllCalled;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        puttAllCalled = true;
        super.putAll( m );
    }
}
