/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.jsr330;

public class Target {

    private Long foo;

    private String date;

    public void setFoo(Long foo) {
        this.foo = foo;
    }

    public Long getFoo() {
        return foo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
