/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcardextends;

/**
 * @author Ben Zegveld
 */
class WildcardedInterfaceImpl implements WildcardedInterface {

    private String contents;

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        this.contents = contents;
    }

}
