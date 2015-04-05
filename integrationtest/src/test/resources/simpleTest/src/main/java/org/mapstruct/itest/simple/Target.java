/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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

    public String getFormNested() {
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
