/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import java.util.List;

public class Source {

    private int foo;
    private Long bar;
    private int qax;
    private Long baz;
    private int zip;
    private String someType;
    private SomeType forNested;
    private List<? extends YetAnotherType> extendsBound;

    public int getFoo() {
        return foo;
    }

    public void setFoo(int foo) {
        this.foo = foo;
    }

    public Long getBar() {
        return bar;
    }

    public void setBar(Long bar) {
        this.bar = bar;
    }

    public int getQax() {
        return qax;
    }

    public void setQax(int qax) {
        this.qax = qax;
    }

    public Long getBaz() {
        return baz;
    }

    public void setBaz(Long baz) {
        this.baz = baz;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getSomeType() {
        return someType;
    }

    public void setSomeType(String someType) {
        this.someType = someType;
    }

    public SomeType getForNested() {
        return forNested;
    }

    public void setForNested(SomeType forNested) {
        this.forNested = forNested;
    }

    public List<? extends YetAnotherType> getExtendsBound() {
        return extendsBound;
    }

    public void setExtendsBound(List<? extends YetAnotherType> extendsBound) {
        this.extendsBound = extendsBound;
    }

}
