/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.typebounds;

/**
 * @author Ben Zegveld
 */
class WildcardImpl implements Wildcard {

    private String contents;
    private boolean shouldMap;

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public boolean hasContents() {
        return shouldMap;
    }

    public void setShouldMap(boolean shouldMap) {
        this.shouldMap = shouldMap;
    }
}
