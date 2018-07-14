/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.flattening;

public class Stock {
    private  int count;
    private  Article first;
    private  Article second;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Article getFirst() {
        return first;
    }

    public void setFirst(Article first) {
        this.first = first;
    }

    public Article getSecond() {
        return second;
    }

    public void setSecond(Article second) {
        this.second = second;
    }

}
