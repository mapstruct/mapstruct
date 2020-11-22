/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.nestedprop.expanding;

import static java.util.Objects.requireNonNull;

public class FlattenedStock {
    private String article1;
    private String article2;
    private int count;

    public FlattenedStock() {
    }

    public FlattenedStock(String article1, String article2, int count) {
        this.article1 = requireNonNull( article1 );
        this.article2 = requireNonNull( article2 );
        this.count = count;
    }

    public String getArticle1() {
        return article1;
    }

    public void setArticle1(String article1) {
        this.article1 = article1;
    }

    public String getArticle2() {
        return article2;
    }

    public void setArticle2(String article2) {
        this.article2 = article2;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
