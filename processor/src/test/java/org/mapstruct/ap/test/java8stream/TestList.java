/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Sjaak Derksen
 */
public class TestList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;

    private static boolean addAllCalled = false;

    public static boolean isAddAllCalled() {
        return addAllCalled;
    }

    public static void setAddAllCalled(boolean addAllCalled) {
        TestList.addAllCalled = addAllCalled;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addAllCalled = true;
        return super.addAll( c );
    }
}
