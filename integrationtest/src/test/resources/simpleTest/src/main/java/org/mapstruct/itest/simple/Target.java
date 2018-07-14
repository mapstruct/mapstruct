/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import java.util.List;
import org.mapstruct.itest.simple.SomeType;

public class Target {

    private Long foo;
    private int bar;
    private Long baz;
    private int qax;
    private String zip;
    private SomeType someType;
    private String fromNested;
    private List<String> extendsBound;

    public Long getFoo() {
        return foo;
    }

    public void setFoo(Long foo) {
        this.foo = foo;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    public Long getBaz() {
        return baz;
    }

    public void setBaz(Long baz) {
        this.baz = baz;
    }

    public int getQax() {
        return qax;
    }

    public void setQax(int qax) {
        this.qax = qax;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public SomeType getSomeType() {
        return someType;
    }

    public void setSomeType(SomeType someType) {
        this.someType = someType;
    }

    public String getFromNested() {
        return fromNested;
    }

    public void setFromNested(String fromNested) {
        this.fromNested = fromNested;
    }

    public List<String> getExtendsBound() {
        return extendsBound;
    }

    public void setExtendsBound(List<String> extendsBound) {
        this.extendsBound = extendsBound;
    }
}
